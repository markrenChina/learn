/**
 * WebSocket????, MyWebSocketSession.cpp
 * zhangyl 2017.03.09
 */
#include "MyWebSocketSession.h"
#include <sstream>
#include <list>

#ifndef WIN32
#include <strings.h>
#endif

#include "../net/EventLoopThread.h"
#include "../base/Singleton.h"
#include "../base/AsyncLog.h"
#include "../base/Platform.h"
#include "../utils/StringUtil.h"
#include "../utils/UUIDGenerator.h"
#include "../zlib1.2.11/ZlibUtil.h"

#include "WebSocketHandshake.h"

//client?????????http??50M
#define MAX_WEBSOCKET_CLIENT_PACKAGE_LENGTH        50 * 1024 * 1024
//??§³websocket?????§³
#define MIN_WEBSOCKET_PACKAGE_HEADER_LENGTH        6

//?????????????§³??10M
#define MAX_WEBSOCKET_SERVER_PACKAGE_LENGTH 10 * 1024 * 1024

MyWebSocketSession::MyWebSocketSession(std::shared_ptr<TcpConnection>& conn) : m_bUpdateToWebSocket(false), m_tmpConn(conn), m_bClientCompressed(false)
{
    m_strClientInfo = conn->peerAddress().toIpPort();
}

void MyWebSocketSession::onRead(const std::shared_ptr<TcpConnection>& conn, Buffer* pBuffer, Timestamp receivTime)
{
    while (true)
    {
        /**
        GET /realtime HTTP/1.1
        Host: 127.0.0.1:9989
        Connection: Upgrade
        Pragma: no-cache
        Cache-Control: no-cache
        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36
        Upgrade: websocket
        Origin: http://coolaf.com
        Sec-WebSocket-Version: 13
        Accept-Encoding: gzip, deflate, br
        Accept-Language: zh-CN,zh;q=0.9,en;q=0.8
        Sec-WebSocket-Key: IqcAWodjyPDJuhGgZwkpKg==
        Sec-WebSocket-Extensions: permessage-deflate; client_max_window_bits
       */
        size_t readableBytesCount = pBuffer->readableBytes();
        if (readableBytesCount >= MAX_WEBSOCKET_CLIENT_PACKAGE_LENGTH)
        {
            this->close();
            return;
        }

        //WEBSOCKET RFC ?????https://www.rfc-editor.org/rfc/rfc6455.txt


        /*
          0                   1                   2                   3
          0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
         +-+-+-+-+-------+-+-------------+-------------------------------+
         |F|R|R|R| opcode|M| Payload len |    Extended payload length    |
         |I|S|S|S|  (4)  |A|     (7)     |             (16/64)           |
         |N|V|V|V|       |S|             |   (if payload len==126/127)   |
         | |1|2|3|       |K|             |                               |
         +-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
         |     Extended payload length continued, if payload len == 127  |
         + - - - - - - - - - - - - - - - +-------------------------------+
         |                               |Masking-key, if MASK set to 1  |
         +-------------------------------+-------------------------------+
         | Masking-key (continued)       |          Payload Data         |
         +-------------------------------- - - - - - - - - - - - - - - - +
         :                     Payload Data continued ...                :
         + - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
         |                     Payload Data continued ...                |
         +---------------------------------------------------------------+

         */

         //??¦Ä???????????????§¿?????????
        if (!m_bUpdateToWebSocket)
        {
            const char* pos = pBuffer->findString("\r\n\r\n");
            bool foundEngTag = (pos != nullptr);

            if (!foundEngTag)
            {
                //?????????
                if (pBuffer->readableBytes() < MAX_WEBSOCKET_CLIENT_PACKAGE_LENGTH)
                    return;
                //????????
                else
                {
                    conn->forceClose();
                    return;
                }
            }

            //4??\r\n\r\n?????
            size_t length = pos - (pBuffer->peek()) + 4;
            std::string currentData(pBuffer->peek(), length);
            pBuffer->retrieve(length);

            if (!handleHandshake(currentData, conn))
            {
                conn->forceClose();
                return;
            }

            LOGI("websocket message: %s", currentData.c_str());
        }
        //???????
        else
        {
            if (readableBytesCount < MIN_WEBSOCKET_PACKAGE_HEADER_LENGTH)
                return;
            
            if (!decodePackage(pBuffer, conn))
                conn->forceClose();
                
            //????????????????????????????????????
            return;
        }
    }
    
    //???????????????    
}

bool MyWebSocketSession::decodePackage(Buffer* pBuffer, const std::shared_ptr<TcpConnection>& conn)
{
    size_t readableBytesCount = pBuffer->readableBytes();
    
    const int32_t TWO_FLAG_BYTES = 2;

    //?????????
    const int32_t MAX_HEADER_LENGTH = 14;
    char pBytes[MAX_HEADER_LENGTH] = {0};
    //??????????????????????????????????????????
    if (readableBytesCount > MAX_HEADER_LENGTH)
        memcpy(pBytes, pBuffer->peek(), MAX_HEADER_LENGTH * sizeof(char));
    else
        memcpy(pBytes, pBuffer->peek(), readableBytesCount * sizeof(char));

    bool FIN = (pBytes[0] & 0x80);
    //TODO: ??????§µ????????????????¦Ä???????????????
    //bool RSV1, RSV2, RSV3;
    //???????????4¦Ë???????????
    int32_t opcode = (int32_t)(pBytes[0] & 0xF);

    //if (!FIN && opcode != MyOpCode::CONTINUATION_FRAME)
    //{
    //    LOGE("FIN did not match opcode, client: %s", conn->peerAddress().toIpPort().c_str());
    //    return false;
    //}

    //if (opcode == MyOpCode::CLOSE)
    //{
    //    LOGE("receive CLOSE opcode, client: %s", conn->peerAddress().toIpPort().c_str());
    //    return false;
    //}

    //?????????????¦Ë??????????????????????????????¦Á????????1
    bool mask = ((pBytes[1] & 0x80));
    //if (!mask)
    //{
    //    LOGE("invalid mask flag, client: %s", conn->peerAddress().toIpPort().c_str());
    //    return false;
    //}

    int32_t headerSize = 0;
    int64_t bodyLength = 0;
    //??mask??????????????masking-key????
    if (mask)
        headerSize += 4;

    //?????????????¦Ë
    int32_t payloadLength = (int32_t)(pBytes[1] & 0x7F);
    if (payloadLength <= 0 && payloadLength > 127)
    {
        LOGE("invalid payload length, payloadLength: %d, client: %s", payloadLength, conn->peerAddress().toIpPort().c_str());
        return false;
    }

    if (payloadLength > 0 && payloadLength <= 125)
    {
        headerSize += TWO_FLAG_BYTES;
        bodyLength = payloadLength;
    }               
    else if (payloadLength == 126)
    {
        headerSize += TWO_FLAG_BYTES;
        headerSize += sizeof(short);

        if ((int32_t)readableBytesCount < headerSize)
            return true;

        short tmp;
        memcpy(&tmp, &pBytes[2], 2);
        int32_t extendedPayloadLength = ::ntohs(tmp);
        bodyLength = extendedPayloadLength;
        //???á½??????????
        if (bodyLength < 126 || bodyLength > UINT16_MAX)
        {
            LOGE("illegal extendedPayloadLength, extendedPayloadLength: %d, client: %s", bodyLength, conn->peerAddress().toIpPort().c_str());
            return false;
        }        
    }
    else if (payloadLength == 127)
    {
        headerSize += TWO_FLAG_BYTES;
        headerSize += sizeof(uint64_t);
        
        //?????????
        if ((int32_t)readableBytesCount < headerSize)
            return true;

        int64_t tmp;
        memcpy(&tmp, &pBytes[2], 8);
        int64_t extendedPayloadLength = ::ntohll(tmp);
        bodyLength = extendedPayloadLength;
        //???á½??????????
        if (bodyLength <= UINT16_MAX)
        {
            LOGE("illegal extendedPayloadLength, extendedPayloadLength: %lld, client: %s", bodyLength, conn->peerAddress().toIpPort().c_str());
            return false;
        }        
    }

    if ((int32_t)readableBytesCount < headerSize + bodyLength)
        return true;

    //??????
    pBuffer->retrieve(headerSize);
    std::string payloadData(pBuffer->peek(), bodyLength);
    //???????
    pBuffer->retrieve(bodyLength);

    if (mask)
    {
        char maskingKey[4] = { 0 };
        //headerSize - 4??masking-key??¦Ë??
        memcpy(maskingKey, pBytes + headerSize - 4, 4);
        unmaskData(payloadData, maskingKey);
    }
    
    if (FIN)
    {
        //?????????????????????????§Ö????????
        m_strParsedData.append(payloadData);
        //?????????
        if (!processPackage(m_strParsedData, (MyOpCode)opcode, conn))
            return false;

        m_strParsedData.clear();
    }
    else
    {
        //??????????????????????
        m_strParsedData.append(payloadData);
    } 

    return true;
}

std::string MyWebSocketSession::getHeader(const char* headerField) const
{
    auto iter = m_mapHttpHeaders.find(headerField);
    if (iter == m_mapHttpHeaders.end())
        return "";

    return iter->second;
}

std::string MyWebSocketSession::getHeaderIgnoreCase(const char* headerField) const
{
    for (const auto& iter : m_mapHttpHeaders)
    {
#ifdef WIN32
        if (stricmp(iter.first.c_str(), headerField) == 0)
#else
        if (strcasecmp(iter.first.c_str(), headerField) == 0)      
#endif
        {
            return iter.second;
        }
    }

    return "";
}

void MyWebSocketSession::close()
{
    if (m_tmpConn.expired())
        return;

    std::shared_ptr<TcpConnection> conn = m_tmpConn.lock();
    conn->forceClose();
}

bool MyWebSocketSession::handleHandshake(const std::string& data, const std::shared_ptr<TcpConnection>& conn)
{
    std::vector<std::string> vecHttpHeaders;
    StringUtil::Split(data, vecHttpHeaders, "\r\n");
    //??????3??
    if (vecHttpHeaders.size() < 3)
        return false;

    std::vector<std::string> v;
    size_t vecLength = vecHttpHeaders.size();
    for (size_t i = 0; i < vecLength; ++i)
    {
        //????§Ý?¨°????????§¿??·Ú??
        if (i == 0)
        {
            if (!parseHttpPath(vecHttpHeaders[i]))
                return false;
        }
        else
        {
            //????????
            v.clear();
            StringUtil::Cut(vecHttpHeaders[i], v, ":");
            if (v.size() < 2)
                return false;

            StringUtil::trim(v[1]);
            m_mapHttpHeaders[v[0]] = v[1];
        }
    }

    /* TODO????????????? 
        ??????????????§¹?? HTTP ????
        ????????????? GET,?? HTTP ?·Ú?????? 1.1
        ????? REQUEST-URI ???????????ÕÇ?????(????? Page 13)
        ?????????? Host ?
        ?????????? Upgrade: websocket ?,?????? websocket
        ?????????? Connection: Upgrade ?,?????? Upgrade
        ?????????? Sec-WebSocket-Key ?
        ?????????? Sec-WebSocket-Version: 13 ?,?????? 13
        ?????????? Origin ?
        ?????????? Sec-WebSocket-Protocol ?,?ÕÇ??§¿??
        ?????????? Sec-WebSocket-Extensions ,?ÕÇ§¿?????
        ?????????????????,?? cookie ??
     */

    auto target = m_mapHttpHeaders.find("Connection");
    if (target == m_mapHttpHeaders.end() || target->second != "Upgrade")
        return false;

    target = m_mapHttpHeaders.find("Upgrade");
    if (target == m_mapHttpHeaders.end() || target->second != "websocket")
        return false;

    target = m_mapHttpHeaders.find("Host");
    if (target == m_mapHttpHeaders.end() || target->second.empty())
        return false;

    target = m_mapHttpHeaders.find("Origin");
    if (target == m_mapHttpHeaders.end() || target->second.empty())
        return false;

    //TODO: ????????????§³§Õ??
    target = m_mapHttpHeaders.find("User-Agent");
    if (target != m_mapHttpHeaders.end())
    {
        m_strUserAgent = target->second;
    }

    target = m_mapHttpHeaders.find("Sec-WebSocket-Extensions");
    if (target != m_mapHttpHeaders.end())
    {
        std::vector<std::string> vecExtensions;
        StringUtil::Split(target->second, vecExtensions, ";");

        for (const auto& iter : vecExtensions)
        {
            if (iter == "permessage-deflate")
            {
                m_bClientCompressed = true;
                break;
            }
        }
    }

    target = m_mapHttpHeaders.find("Sec-WebSocket-Key");
    if (target == m_mapHttpHeaders.end() || target->second.empty())
        return false;

    char secWebSocketAccept[29] = {};
    js::WebSocketHandshake::generate(target->second.c_str(), secWebSocketAccept);
    std::string response;
    makeUpgradeResponse(secWebSocketAccept, response);
    conn->send(response);
    
    m_bUpdateToWebSocket = true;

    //???????????§Õ????
    onConnect();

    return true;
}

bool MyWebSocketSession::parseHttpPath(const std::string& str)
{
    std::vector<std::string> vecTags;
    StringUtil::Split(str, vecTags, " ");
    if (vecTags.size() != 3)
        return false;

    //TODO: ??¨°??????§³§Õ????
    if (vecTags[0] != "GET")
        return false;

    std::vector<std::string> vecPathAndParams;
    StringUtil::Split(vecTags[1], vecPathAndParams, "?");
    //?????????¡¤??????
    if (vecPathAndParams.empty())
        return false;

    m_strURL = vecPathAndParams[0];
    if (vecPathAndParams.size() >= 2)
        m_strParams = vecPathAndParams[1];

    //WebSocket§¿??·Ú?????1.1
    if (vecTags[2] != "HTTP/1.1")
        return false;

    return true;
}

void MyWebSocketSession::makeUpgradeResponse(const char* secWebSocketAccept, std::string& response)
{
    response = "HTTP/1.1 101 Switching Protocols\r\n"
               "Upgrade: websocket\r\n"
               "Sec-Websocket-Accept: ";  
    response += secWebSocketAccept;
    response += "\r\nServer: BTCMEXWebsocketServer 1.0.0\r\n";
    response += "Connection: Upgrade\r\n"
                "Sec-WebSocket-Version: 13\r\n";
    if (m_bClientCompressed)
        response += "Sec-WebSocket-Extensions: permessage-deflate; client_no_context_takeover\r\n";

    response += "Date: ";

    char szNow[64];
    time_t now = time(NULL);
    tm time;
#ifdef _WIN32
    localtime_s(&time, &now);
#else
    localtime_r(&now, &time);
#endif
    strftime(szNow, sizeof(szNow), "%Y%m%d%H%M%S", &time);
    response += szNow;
    response += "\r\n\r\n";   
}

void MyWebSocketSession::unmaskData(std::string& src, const char* maskingKey)
{
    /*
     *  ???????Masking-key??????????????????? 32 ¦Ë??????????????????????????????????????????????????????????????

        ????????Ñs

        original-octet-i??????????? i ????
        transformed-octet-i??????????????? i ????
        j???i mod 4??????
        masking-key-octet-j??? mask key ?? j ????
        ????????? original-octet-i ?? masking-key-octet-j ?????? transformed-octet-i??

        j  = i MOD 4
        transformed-octet-i = original-octet-i XOR masking-key-octet-j
     */
    char j;
    for (size_t n = 0; n < src.length(); ++n)
    {
        j = n % 4;
        src[n] = src[n] ^ maskingKey[j];
    }
}

bool MyWebSocketSession::processPackage(const std::string& data, MyOpCode opcode, const std::shared_ptr<TcpConnection>& conn)
{
    if (opcode == MyOpCode::CLOSE)
    {
        LOGE("received CLOSE opcode, close session, client: %s", conn->peerAddress().toIpPort().c_str());
        return false;
    }
    else if (opcode == MyOpCode::PING)
    {
        onPing();
    }
    else if (opcode == MyOpCode::PONG)
    {
        onPong();
    }
    else if (opcode == MyOpCode::TEXT_FRAME || opcode == MyOpCode::BINARY_FRAME)
    {
        std::string out;
        if (m_bClientCompressed)
        {
            if (!ZlibUtil::inflate(data, out))
            {
                LOGE("uncompress failed, dataLength: %d, client: %s", data.length(), conn->peerAddress().toIpPort().c_str());
                return false;
            }
        }
        else
            out = data;

        LOGI("receid data: %s, client: %s", out.c_str(), conn->peerAddress().toIpPort().c_str());

        onMessage(out);
    }
    
    return true;
}

void MyWebSocketSession::send(const std::string& data, int32_t opcode/* = MyOpCode::TEXT_FRAME*/, bool compress/* = false*/)
{
    /*
          0                   1                   2                   3
          0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
         +-+-+-+-+-------+-+-------------+-------------------------------+
         |F|R|R|R| opcode|M| Payload len |    Extended payload length    |
         |I|S|S|S|  (4)  |A|     (7)     |             (16/64)           |
         |N|V|V|V|       |S|             |   (if payload len==126/127)   |
         | |1|2|3|       |K|             |                               |
         +-+-+-+-+-------+-+-------------+ - - - - - - - - - - - - - - - +
         |     Extended payload length continued, if payload len == 127  |
         + - - - - - - - - - - - - - - - +-------------------------------+
         |                               |Masking-key, if MASK set to 1  |
         +-------------------------------+-------------------------------+
         | Masking-key (continued)       |          Payload Data         |
         +-------------------------------- - - - - - - - - - - - - - - - +
         :                     Payload Data continued ...                :
         + - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - +
         |                     Payload Data continued ...                |
         +---------------------------------------------------------------+
     */

    size_t dataLength = data.length();
    std::string destbuf;
    if (m_bClientCompressed && dataLength > 0)
    {
        if (!ZlibUtil::deflate(data, destbuf))
        {
            LOGE("compress buf error, data: %s", data.c_str());
            return;
        }
    }
    else
        destbuf = data;

    LOGI("destbuf.length(): %d", destbuf.length());
    
    dataLength = destbuf.length();

    char firstTwoBytes[2] = { 0 };
    //FIN
    firstTwoBytes[0] |= 0x80;

    //if (!(flags & SND_CONTINUATION)) {
    //opcode
    firstTwoBytes[0] |= opcode;
    //}

    //TODO?????????????????
    const char compressFlag = 0x40;
    if (m_bClientCompressed)
        firstTwoBytes[0] |= compressFlag;
    
    //mask = 0;
    //????????????
    std::string actualSendData;

    if (dataLength < 126)
    {
        firstTwoBytes[1] = dataLength;
        actualSendData.append(firstTwoBytes, 2);
    }
    else if (dataLength <= UINT16_MAX)  //2????????????????????65535??
    {
        firstTwoBytes[1] = 126;
        char extendedPlayloadLength[2] = { 0 };
        uint16_t tmp = ::htons(dataLength);
        memcpy(&extendedPlayloadLength, &tmp, 2);
        actualSendData.append(firstTwoBytes, 2);
        actualSendData.append(extendedPlayloadLength, 2);
    }
    else
    {
        firstTwoBytes[1] = 127;
        char extendedPlayloadLength[8] = { 0 };
        uint64_t tmp = ::htonll((uint64_t)dataLength);
        memcpy(&extendedPlayloadLength, &tmp, 8);
        actualSendData.append(firstTwoBytes, 2);
        actualSendData.append(extendedPlayloadLength, 8);
    }   
    
    actualSendData.append(destbuf);

    sendPackage(actualSendData.c_str(), actualSendData.length());

    //LOG_DEBUG_BIN((unsigned char*)actualSendData.c_str(), actualSendData.length());
}

void MyWebSocketSession::send(const char* data, size_t dataLength, int32_t opcode/* = MyOpCode::TEXT_FRAME*/, bool compress/* = false*/)
{
    std::string str(data, dataLength);
    send(str, opcode, compress);
}

void MyWebSocketSession::sendAndClose(const char* data, size_t dataLength, bool compress/* = false*/)
{
    send(data, dataLength, MyOpCode::CLOSE, compress);
}

void MyWebSocketSession::onPing()
{
    send("", 0, MyOpCode::PONG, m_bClientCompressed);
}

void MyWebSocketSession::sendPackage(const char* data, size_t length)
{
    if (m_tmpConn.expired())
        return;

    std::shared_ptr<TcpConnection> conn = m_tmpConn.lock();
    conn->send(data, length);
}
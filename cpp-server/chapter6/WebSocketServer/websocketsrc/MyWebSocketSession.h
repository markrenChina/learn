/**
 * WebSocket????, MyWebSocketSession.h
 * zhangyl 2019.08.28
 */
#ifndef __MY_WEBSOCKET_SESSION_H__
#define __MY_WEBSOCKET_SESSION_H__

#include "../base/Timestamp.h"

#include "../net/Buffer.h"
#include "../net/TcpConnection.h"

#include <string>
#include <map>
#include <memory>

using namespace net;

// 4 bits
enum MyOpCode : unsigned char
{
    CONTINUATION_FRAME  = 0x0,
    TEXT_FRAME          = 0x1,
    BINARY_FRAME        = 0x2,
    RESERVED1           = 0x3,
    RESERVED2           = 0x4,
    RESERVED3           = 0x5,
    RESERVED4           = 0x6,
    RESERVED5           = 0x7,
    CLOSE               = 0x8,
    PING                = 0x9,
    PONG                = 0xA,
    RESERVED6           = 0xB,
    RESERVED7           = 0xC,
    RESERVED8           = 0xD,
    RESERVED9           = 0xE,
    RESERVED10          = 0xF
};

class MyWebSocketSession
{
public:
    MyWebSocketSession(std::shared_ptr<TcpConnection>& conn);
    virtual ~MyWebSocketSession() = default;

    MyWebSocketSession(const MyWebSocketSession& rhs) = delete;
    MyWebSocketSession& operator =(const MyWebSocketSession& rhs) = delete;

public:
    const char* getClientInfo() const
    {
        return m_strClientInfo.c_str();
    }

    const char* getUserAgent() const
    {
        return m_strUserAgent.c_str();
    }

    std::shared_ptr<TcpConnection> getConnectionPtr()
    {
        if (m_tmpConn.expired())
            return NULL;

        return m_tmpConn.lock();
    }

    std::string getHeader(const char* headerField) const;
    std::string getHeaderIgnoreCase(const char* headerField) const;

    std::string getUrl() const
    {
        return m_strURL;
    }

    std::string getParams() const
    {
        return m_strParams;
    }

    void close();
       
    void send(const std::string& data, int32_t opcode = MyOpCode::TEXT_FRAME, bool compress = false);
    void send(const char* data, size_t dataLength, int32_t opcode = MyOpCode::TEXT_FRAME, bool compress = false);
    void sendAndClose(const char* data, size_t dataLength, bool compress = false);

    //???????§Õ
    virtual void onConnect()
    {

    }

    //???????§Õ
    virtual void onDisconnect()
    {

    }

    //?????§Õ?¨²??????????
    virtual bool onMessage(const std::string& strClientMsg)
    {
        return false;
    }

    //?????§Õ?¨²??????????
    virtual void onPing();

    //?????§Õ?¨²??????????
    virtual void onPong()
    {

    }

    //????????, ?????????loop????
    void onRead(const std::shared_ptr<TcpConnection>& conn, Buffer* pBuffer, Timestamp receivTime);
    
private:
    bool handleHandshake(const std::string& data, const std::shared_ptr<TcpConnection>& conn);
    bool decodePackage(Buffer* pBuffer, const std::shared_ptr<TcpConnection>& conn);
    bool parseHttpPath(const std::string& str);
    void makeUpgradeResponse(const char* secWebSocketAccept, std::string& response);
    void unmaskData(std::string& src, const char* maskingKey);
    bool processPackage(const std::string& data, MyOpCode opcode, const std::shared_ptr<TcpConnection>& conn);

    void sendPackage(const char* data, size_t length);

protected:
    std::string                  m_strClientInfo;
    std::string                  m_strUserAgent;

private:
    //???????????http§¿??
    bool                               m_bUpdateToWebSocket;
    std::weak_ptr<TcpConnection>       m_tmpConn;
    //key ??http???????value ??http????
    std::map<std::string, std::string> m_mapHttpHeaders;
    //????¡¤??
    std::string                        m_strURL;
    //???????????
    std::string                        m_strParams;

    //?????????
    std::string                        m_strParsedData;

    //??????????????
    bool                               m_bClientCompressed;
};


#endif //!__MY_WEBSOCKET_SESSION_H__
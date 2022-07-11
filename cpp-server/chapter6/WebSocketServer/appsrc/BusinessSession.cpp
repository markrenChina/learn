/***************************************************************
 * ??????? :
 * ?????   : BusinessSession.h
 * ????     : zhangyl
 * ?·Ú     : 1.0.0.0
 * ???????? : 2019.03.29
 * ????     :
 ***************************************************************/
#include "BusinessSession.h"

std::string BusinessSession::m_strWelcomeMsg = "Welcome to WebSocket Server, contact: balloonwj@qq.com";

BusinessSession::BusinessSession(std::shared_ptr<TcpConnection>& conn) : MyWebSocketSession(conn)
{
    
}

bool BusinessSession::onMessage(const std::string& strClientMsg)
{   
    //TODO: ?????????????????????????????????
    send(strClientMsg);

    return true;
}

void BusinessSession::onConnect()
{ 
    //????????
    sendWelcomeMsg();     
}

void BusinessSession::sendWelcomeMsg()
{
    send(BusinessSession::m_strWelcomeMsg);
}
/**
 * WebSocketServer??MyWebSocketServer.h
 * zhangyl 2019.08.28
 */
#ifndef __MY_WEBSOCKET_SERVER_H__
#define __MY_WEBSOCKET_SERVER_H__

#include "MyWebSocketServer.h"

#include <memory>
#include <mutex>
#include <thread>
#include <list>

#include "../net/TcpServer.h"

using namespace net;

class BusinessSession;

class MyWebSocketServer final
{
public:
    MyWebSocketServer();
    ~MyWebSocketServer() = default;
    MyWebSocketServer(const MyWebSocketServer& rhs) = delete;
    MyWebSocketServer& operator =(const MyWebSocketServer& rhs) = delete;

public:
    bool init(const char* ip, short port, EventLoop* loop);
    void uninit();

    //????????????????????????????????conn->connected()???§Ø??????????loop???????
    void onConnection(std::shared_ptr<TcpConnection> conn);
    //??????
    void onClose(const std::shared_ptr<TcpConnection>& conn);

private:
    std::shared_ptr<TcpServer>                             m_server; 

    std::list<std::shared_ptr<BusinessSession>>            m_listSessions;
    std::mutex                                             m_mutexForSession;      //??????????m_sessions

    //????????????ip???
    std::string                                            m_strWsHost;
    //???????????
    int                                                    m_wsPort;
};

#endif //!__MY_WEBSOCKET_SERVER_H__
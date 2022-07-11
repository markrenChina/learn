//
// Created by Ushop on 2022/6/24.
//
bool EventLoop::createWakeupfd(){
#ifdef WIN32
    m_wakeupFdListen = sockets::createOrDie();
    m_wakeupFdSend = socket::createOrDie();

    //??Windows ????????????socket
    struct sockaddr_in bindaddr;
    bindaddr.sin_family = AF_INET;
    bindaddr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
    //??port???0????????bind
    bindaddr.sin_port = 0;
    sockets::setReuseAddr(m_wakeupFdListen,true);
    sockets::bindOrDie(m_wakeupFdListen,bindaddr);
    sockets::listenOrDie(m_wakeupFdListen);

    struct sockaddr_in serveraddr;
    int serveraddrlen = sizeof(serveraddr);
    //???????getsockname???port????????????????????????
    if(getsockname(m_wakeupFdListen,(sockaddr*)&serveraddr,&serveraddrlen) < 0){
        //?車?????
        LOGF("Unable to bind address info, EventLoop: 0x%x", this);
        return false;
    }
    int useport = ntohs(serveraddr.sin_port);
    LOGD("wakeup fd use port: %d",useport);

    //serveraddr.sin_family = AF_INET'
    //serveraddr.sin_addr.s_addr = inet_addr("127.0.0.1")
    //serveraddr.sin_port = htons(INNER_WAKEUP_LISTEN_PORT);
    if(::connect(m_wakeupFdSend,(struct sockaddr*)&serveraddr,sizeof(serveraddr)) < 0) {
        //?車?????
        LOGF("Unable to connect to wakeup peer, EventLoop: 0x%x",this);
        return false;
    }

    struct socketaddr_in clientaddr;
    socklen_t clientaddrlen = sizeof(clientaddr);
    m_wakeupFdRecv = ::accept(m_wakeupFdListen,(struct sockaddr*)&clientaddr,&clientaddrlen);
    if(m_wakeupFdRecv < 0){
        //?車?????
        LOGF("Unable to accept to wakeup peer, EventLoop: 0x%x",this);
        return false;
    }
    sockets::setNonBlockAndCloseOnExec(m_wakeupFdSend);
    sockets::setNonBlockAndCloseOnExec(m_wakeupFdRecv);
#else
    //??Linux???????eventfd??????????????忱
    m_wakeupFd = ::eventfd(0,EFD_NONBLOCK | EFD_CLOEXEC);
    if(m_wakeupFd < 0){
        //?車?????
        LOGF("Unable to create wakeup eventfd, EventLoop: 0x%x",this);
        return false;
    }
#endif
    return true;
}

//????????????
bool EventLoop::wakeup(){
    //????one????忱???????????????fd?????????????????????????
    uint64_t one = 1;
#ifdef WIN32
    int32_t n = sockets::write(m_wakeupFdSend,&one,sizeof(one));
#else
    int32_t n = sockets::write(m_wakeupFd,&one,sizeof(one));
#endif
    if(n != sizeof one) {
#ifdef WIN32
        DWORD error = ::WSAGetLastError();
        LOGSYSE("EventLoop::wakeup() writes %d bytes %d bytes instead of 8,fd: %d,error: %d",n,m_wakeupFdSend,(int32_t)error);
#else
        int error = errno;
        LOGSYSE("EventLoop::wakeup() writes %d bytes %d bytes instead of 8,fd: %d,error: %d",n,error,strerror(error));
#endif
        return false;
    }
    return true;
}

//?????fd?忪???????
bool EventLoop::handleRead() {
    uint64_t one = 1;
#ifdef WIN32
    int32_t n = sockets::read(m_wakeupFdRecv,&one,sizeof(one));
#else
    int32_t n = sockets::read(m_wakeupFd,&one,sizeof(one));
#endif
    if(n != sizeof one){
#ifdef WIN32
        DWORD error = ::WASGetLastError();
        LOGSYSE("EventLoop::wakeup() read %d bytes %d bytes instead of 8,fd: %d,error: %d",n,m_wakeupFdRecv,(int32_t)error);
#else
        int error = errno;
        LOGSYSE("EventLoop::wakeup() read %d bytes %d bytes instead of 8,fd: %d,error: %d",n,error,strerror(error));
#endif
        return false;
    }
    return true;
}
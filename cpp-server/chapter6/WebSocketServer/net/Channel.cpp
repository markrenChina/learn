#include "Channel.h"
#include <sstream>

#include "../base/Platform.h"
#include "../base/AsyncLog.h"
#include "Poller.h"
#include "EventLoop.h"

using namespace net;

const int Channel::kNoneEvent = 0;
const int Channel::kReadEvent = XPOLLIN | XPOLLPRI;
const int Channel::kWriteEvent = XPOLLOUT;

Channel::Channel(EventLoop* loop, int fd__): loop_(loop),
                                            fd_(fd__),
                                            events_(0),
                                            revents_(0),
                                            index_(-1),
                                            logHup_(true),
                                            tied_(false)/*,
                                            eventHandling_(false),
                                            addedToLoop_(false)
                                            */
{
}

Channel::~Channel()
{
	//assert(!eventHandling_);
	//assert(!addedToLoop_);
	if (loop_->isInLoopThread())
	{
		//assert(!loop_->hasChannel(this));
	}
}

void Channel::tie(const std::shared_ptr<void>& obj)
{
	tie_ = obj;
	tied_ = true;
}

bool Channel::enableReading() 
{ 
    events_ |= kReadEvent;
    return update();
}

bool Channel::disableReading()
{
    events_ &= ~kReadEvent; 
    
    return update();
}

bool Channel::enableWriting() 
{
    events_ |= kWriteEvent; 
    
    return update(); 
}

bool Channel::disableWriting()
{ 
    events_ &= ~kWriteEvent; 
    return update();
}

bool Channel::disableAll()
{ 
    events_ = kNoneEvent; 
    return update(); 
}

bool Channel::update()
{
	//addedToLoop_ = true;
	return loop_->updateChannel(this);
}

void Channel::remove()
{
	if (!isNoneEvent())
        return;
	//addedToLoop_ = false;
	loop_->removeChannel(this);
}

void Channel::handleEvent(Timestamp receiveTime)
{
	std::shared_ptr<void> guard;
	if (tied_)
	{
		guard = tie_.lock();
		if (guard)
		{
			handleEventWithGuard(receiveTime);
		}
	}
	else
	{
		handleEventWithGuard(receiveTime);
	}
}

void Channel::handleEventWithGuard(Timestamp receiveTime)
{
	//eventHandling_ = true;
    /*
    XPOLLIN ???????
    XPOLLPRI???????????????????????????tcp socket?????????
    POLLRDNORM , ???????????????????????????
    POLLRDBAND ,????????????????????????????????
    XPOLLOUT??��???
    POLLWRNORM , ��??????????????????��
    POLLWRBAND ,??��???????????????????��??????   ????????
    XPOLLRDHUP (since Linux 2.6.17)??Stream socket???????????????????stream socket?????????????raw socket,dgram socket??????????��????????????????????????????????_GNU_SOURCE ?????????????????��???��?????????????????????????????????????????????????????????????????
    ????#define _GNU_SOURCE
      ????#include <poll.h>
    XPOLLERR??????????????????????revents??????��????????
    XPOLLHUP??????????????????????revents??????��?????????poll??????fd??socket????????socket?????????????????????????????????socket()????????????��???connect??
    XPOLLNVAL??????????????????????revents?????????????????????fd??��?
    */
	LOGD(reventsToString().c_str());
	if ((revents_ & XPOLLHUP) && !(revents_ & XPOLLIN))
	{
		if (logHup_)
		{
			LOGW("Channel::handle_event() XPOLLHUP");
		}
		if (closeCallback_) closeCallback_();
	}

	if (revents_ & XPOLLNVAL)
	{
		LOGW("Channel::handle_event() XPOLLNVAL");
	}

	if (revents_ & (XPOLLERR | XPOLLNVAL))
	{
		if (errorCallback_) 
            errorCallback_();
	}
    
	if (revents_ & (XPOLLIN | XPOLLPRI | XPOLLRDHUP))
	{
		//????????socket???readCallback_???Acceptor::handleRead
        //????????socket???????TcpConnection::handleRead 
        if (readCallback_) 
            readCallback_(receiveTime);
	}

	if (revents_ & XPOLLOUT)
	{
		//???????????????socket????writeCallback_???Connector::handleWrite()
        if (writeCallback_) 
            writeCallback_();
	}
	//eventHandling_ = false;
}

string Channel::reventsToString() const
{
	std::ostringstream oss;
	oss << fd_ << ": ";
	if (revents_ & XPOLLIN)
		oss << "IN ";
	if (revents_ & XPOLLPRI)
		oss << "PRI ";
	if (revents_ & XPOLLOUT)
		oss << "OUT ";
	if (revents_ & XPOLLHUP)
		oss << "HUP ";
	if (revents_ & XPOLLRDHUP)
		oss << "RDHUP ";
	if (revents_ & XPOLLERR)
		oss << "ERR ";
	if (revents_ & XPOLLNVAL)
		oss << "NVAL ";

	return oss.str().c_str();
}

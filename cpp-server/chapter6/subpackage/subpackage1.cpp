//
// Created by Ushop on 2022/6/21.
//
#include <type_traits>
#include <memory>
#include <string>

//伪代码
struct TcpConnection{
    void forceClose();
};
struct Buffer{
    int readableBytes(); //可读取字节数
    const char* peek();  //返回首地址
    void retrieve(size_t size);
};
struct Timestamp;

//强制1字节对齐
#pragma pack(push, 1)
//协议头
struct msg_header {
    std::int32_t bodyszie; //包体大小
};
#pragma pack(pop)

//包的最大字节数限制为10MB
#define MAX_PACKAGE_SIZE 10 * 1024 * 1024

void OnRead(const std::shared_ptr<TcpConnection>& conn,Buffer* pBuffer,Timestamp receivTime){
    while(true){
        if(pBuffer->readableBytes() < (size_t) sizeof(msg_header)){
            //不够一个包头大小，直接退出
            return;
        }

        //取包头信息
        msg_header header;
        memcpy(&header,pBuffer->peek(),sizeof(msg_header));

        //包头有错误，立即关闭连接
        if(header.bodyszie <= 0 || header.bodyszie > MAX_PACKAGE_SIZE){
            //客户端发送非法数据包，服务器主动关闭它
            //LOGE()
            conn->forceClose();
            return;
        }

        //收到数据不够一个完整的包
        if (pBuffer-> readableBytes() < (size_t)header.bodyszie + sizeof(msg_header)) return;
        pBuffer->retrieve(sizeof(msg_header));
        //inbuf用来存放当前要处理的包
        std::string inbuf;
        inbuf.append(pBuffer->peek(),header.bodyszie);
        pBuffer->retrieve(header.bodyszie);
        //解包和业务逻辑
        if (!Process(conn,inbuf.c_str(),inbuf.length())){
            //客户端发送非法数据包，服务器主动关闭它
            //LOGE
            conn->forceClose();
            return;
        }
    }
}

void OnRead(const std::shared_ptr<TcpConnection>& conn,Buffer* pBuffer,Timestamp receivTime){
    while(true){
        if(pBuffer->readableBytes() < (size_t) sizeof(msg_header)){
            //不够一个包头大小，直接退出
            return;
        }

        //取包头信息
        msg_header header;
        memcpy(&header,pBuffer->peek(),sizeof(msg_header));

        //包头有错误，立即关闭连接
        if(header.bodyszie <= 0 || header.bodyszie > MAX_PACKAGE_SIZE){
            //客户端发送非法数据包，服务器主动关闭它
            //LOGE()
            conn->forceClose();
            return;
        }

        //收到数据不够一个完整的包
        if (pBuffer-> readableBytes() < (size_t)header.bodyszie + sizeof(msg_header)) return;
        pBuffer->retrieve(sizeof(msg_header));
        //inbuf用来存放当前要处理的包
        std::string inbuf;
        inbuf.append(pBuffer->peek(),header.bodyszie);
        pBuffer->retrieve(header.bodyszie);
        //解包和业务逻辑
        if (!Process(conn,inbuf.c_str(),inbuf.length())){
            //客户端发送非法数据包，服务器主动关闭它
            //LOGE
            conn->forceClose();
            return;
        }
    }
}

int main(){

}
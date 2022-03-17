package com.ccand99.rxjava1.webserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 指定服务应该做什么
 * 并不处理连接，而是构建一个，处理传入的ByteBuf实例
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {

    private final HttpHandler httpHandler = new HttpHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new HttpServerCodec()) //将原始字节解码为更高级的HTTP请求对象，并且能将响应编码为原始字节
                .addLast(httpHandler); //处理具体的业务
    }
}

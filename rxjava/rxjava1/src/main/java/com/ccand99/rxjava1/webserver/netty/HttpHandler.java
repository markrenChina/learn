package com.ccand99.rxjava1.webserver.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

import static com.ccand99.rxjava1.base.Utils.log;

@ChannelHandler.Sharable//无状态
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            sendResponse(ctx);
        }
    }

    private void sendResponse(ChannelHandlerContext ctx){
        final DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("OK".getBytes(StandardCharsets.UTF_8))
        );
        response.headers().add("Content-length",2);
        //writeAndFlush 与普通的socket write不同，不会阻塞，返回一个ChannelFuture,可以被订阅
        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE); //订阅ChannelFuture并异步关闭，如果不关闭就是长连接
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log("Error " + cause);
        ctx.close();
    }
}

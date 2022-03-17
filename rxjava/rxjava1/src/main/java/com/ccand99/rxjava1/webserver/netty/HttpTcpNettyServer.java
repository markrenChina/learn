package com.ccand99.rxjava1.webserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 最基础的HTTP服务
 */
public class HttpTcpNettyServer {

    public static void main(String[] args) throws InterruptedException {
        //负责接收传入连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理事件，应该接近cpu核心数
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .option(ChannelOption.SO_BACKLOG, 50_000)
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpInitializer())
                    .bind(8088)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

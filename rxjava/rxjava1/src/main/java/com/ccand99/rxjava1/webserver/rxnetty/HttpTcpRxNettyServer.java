package com.ccand99.rxjava1.webserver.rxnetty;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import rx.Observable;

import java.nio.charset.StandardCharsets;

/**
 * 基于TCP/IP的简单HTTP实现
 */
public class HttpTcpRxNettyServer {

    public static final Observable<String> RESPONSE = Observable.just(
            "HTTP/1.1 200 OK\r\n" +
            "Content-length: 2\r\n" +
            "\r\n" +
            "OK");

    public static void main(String[] args) {
        TcpServer.newServer(8081)
                .<String,String>pipelineConfigurator(pipeline -> {
                    pipeline.addLast(new LineBasedFrameDecoder(128));
                    pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                })
                .start(connection -> {
                    Observable<String> output = connection
                            .getInput()
                            .flatMap(line -> {
                                if (!line.isEmpty()){
                                    return RESPONSE;
                                } else {
                                    return Observable.empty();
                                }
                            });
                    return connection.writeAndFlushOnEach(output);
                })
                .awaitShutdown();
    }
}

package com.ccand99.rxjava1.webserver.version2;

import java.io.IOException;

/**
 * 每个连接对应一个线程
 */
public class ThreadPerConnection extends HttpServer{

    public static void main(String[] args) throws IOException {
        new ThreadPerConnection().run(8080);
    }

    @Override
    void handle(ClientConnection clientConnection) {
        new Thread(clientConnection).start();
    }
}

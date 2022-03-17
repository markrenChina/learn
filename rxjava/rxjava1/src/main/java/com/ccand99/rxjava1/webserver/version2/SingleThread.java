package com.ccand99.rxjava1.webserver.version2;

import java.io.IOException;

public class SingleThread extends HttpServer{

    public static void main(String[] args) throws IOException {
        new SingleThread().run(8080);
    }

    @Override
    void handle(ClientConnection clientConnection) {
        clientConnection.run();
    }
}

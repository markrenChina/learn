package com.ccand99.rxjava1.webserver.version2;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池方式
 */
public class ThreadPoolServer extends HttpServer{

    private final ThreadPoolExecutor executor;

    public ThreadPoolServer() {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);
        executor = new ThreadPoolExecutor(100,100,0L, TimeUnit.MILLISECONDS,
                workQueue, (r,ex) -> {
            ((ClientConnection) r).serviceUnavailable();
        });
    }

    public static void main(String[] args) throws IOException {
        new ThreadPoolServer().run(8080);
    }
    @Override
    void handle(ClientConnection clientConnection) {
        executor.execute(clientConnection);
    }
}

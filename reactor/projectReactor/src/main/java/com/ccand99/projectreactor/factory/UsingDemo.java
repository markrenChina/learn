package com.ccand99.projectreactor.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Random;

public class UsingDemo {

    private static final Logger log = LoggerFactory.getLogger(UsingDemo.class);

    public static void main(String[] args) {
        using();
    }

    //try-with-resources方式,此方式自动关闭代码块
    static void CommandModel() {
        try (Connection connection = Connection.newConnection()) {
            connection.getData().forEach(
                    data -> log.info("Received data: {}",data)
            );
        } catch (Exception e){
            log.info("Error: {}",e.getMessage()) ;
        }
    }

    static void using() {
        Flux<String> ioRequestResult = Flux.using(
                //关联Connection生命周期
                Connection::newConnection,
                //转换方式fromIterable
                connection -> Flux.fromIterable(connection.getData()),
                //关闭方法
                Connection::close
        );
        ioRequestResult.subscribe(
                data -> log.info("Received data: {}",data),
                e -> log.info("Error: {}",e.getMessage()),
                () -> log.info("Stream finish")
        );
    }

    //包装一个阻塞API（简化的Connection）
    private static class Connection implements AutoCloseable {

        private final Random random = new Random();

        public Iterable<String> getData() {
            if (random.nextInt(10) < 3) {
                throw new RuntimeException("Communication error");
            }
            return Arrays.asList("Some","data");
        }

        @Override
        public void close() {
            log.info("IO Connection closed");
        }

        public static Connection newConnection() {
            log.info("IO Connection created");
            return new Connection();
        }
    }
}

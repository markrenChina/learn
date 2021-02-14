package com.ccand99.projectreactor.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;

public class CreateDemo {

    private static final Logger log = LoggerFactory.getLogger(CreateDemo.class);

    public static void main(String[] args) throws InterruptedException {
        create();
        Thread.sleep(1000);
    }

    //和push都支持重载溢出策略，能通过注册额外的处理程序清理资源。
    static void create() throws InterruptedException {
        Disposable disposable = Flux.create(emitter -> {
            emitter.onDispose(() -> log.info("Dispose"));
            //将事件推送到发射器。
            emitter.next("test");
        }).subscribe(e -> log.info("onNext: {}", e));
        Thread.sleep(500);
        disposable.dispose();
    }

    //接口桥接官网示例
    interface MyEventListener<T> {
        void onDataChunk(List<T> chunk);
        void processComplete();
    }

    //此类只是为了编译
    static class MyEventProcessor {
        private MyEventListener myEventListener;

        public MyEventProcessor() { }

        void register(MyEventListener myEventListener){
            this.myEventListener = myEventListener;
        }

        List<String> getHistory(long n){ return null; }

        //...
    }

    static void officialDemo1() {
        MyEventProcessor myEventProcessor = new MyEventProcessor();
        Flux<String> bridge = Flux.create(sink -> {
            //接口实现类桥接到Flux
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
        });
    }

    //混合推拉，使用request
    static void pullAndpush() {
        MyEventProcessor myEventProcessor = new MyEventProcessor();
        Flux<String> bridge = Flux.create(sink -> {
            myEventProcessor.register(
                    new MyEventListener<String>() {
                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
            sink.onRequest(n -> {
                //	发出请求时轮询消息。
                List<String> messages = myEventProcessor.getHistory(n);
                for(String s : messages) {
                    //如果消息立即可用，请将其推入接收器。
                    sink.next(s);
                }
            });
        });
    }

}

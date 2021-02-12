package com.ccand99.awesome.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class RxSseEmitter extends SseEmitter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final static AtomicInteger sessionIdSequence = new AtomicInteger(0);
    private final int sessionId = sessionIdSequence.incrementAndGet();
    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Observer<Temperature> subscriber;

    RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new Observer<Temperature>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (disposable == null)this.disposable = d;
                else disposable.dispose();
            }

            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSseEmitter.this.send(temperature);
                    log.info("[{}] << {} ", sessionId, temperature.getValue());
                } catch (IOException  e) {
                    log.warn("[{}] Can not send event to SSE, closing subscription, message: {}",
                            sessionId, e.getMessage());
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                log.warn("[{}] Received sensor error: {}", sessionId, e.getMessage());
                this.disposable.dispose();
            }

            @Override
            public void onComplete() {
                log.warn("[{}] Stream completed", sessionId);
                this.disposable.dispose();
            }
        };

        onCompletion(() -> {
            log.info("[{}] SSE completed", sessionId);
            this.subscriber.onComplete();
        });
        onTimeout(() -> {
            log.info("[{}] SSE timeout", sessionId);
            this.subscriber.onError(new TimeoutException("SSE timeout"));
        });
    }

    Observer<Temperature> getSubscriber() {
        return subscriber;
    }

    int getSessionId() {
        return sessionId;
    }

}

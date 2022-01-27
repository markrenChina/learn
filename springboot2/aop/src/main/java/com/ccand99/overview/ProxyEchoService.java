package com.ccand99.overview;

import java.time.Duration;
import java.time.Instant;

public class ProxyEchoService implements EchoService {

    private final EchoService echoService;

    public ProxyEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public String echo(String message) {
        Instant startTime = Instant.now();
        String res = echoService.echo(message);
        Duration costTime = Duration.between(startTime, Instant.now());
        System.out.println("执行时间：" + costTime.getNano() + " Nano");
        return res;
    }
}

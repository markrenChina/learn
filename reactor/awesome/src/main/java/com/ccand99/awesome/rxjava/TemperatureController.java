package com.ccand99.awesome.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class TemperatureController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TemperatureSensor temperatureSensor;

    public TemperatureController( TemperatureSensor temperatureSensor){
        this.temperatureSensor = temperatureSensor;
    }

    @RequestMapping(value = "/temperature-stream", method = RequestMethod.GET)
    public SseEmitter events(HttpServletRequest request){
        RxSseEmitter rxSseEmitter = new RxSseEmitter();
        temperatureSensor.temperatureStream().subscribe(rxSseEmitter.getSubscriber());
        return rxSseEmitter;
    }
}

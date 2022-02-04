package com.ccand99.features.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 静态的实现方式
 */
public class StaticExecutor implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public void execute(){
        System.out.println("Executing...");
        applicationEventPublisher.publishEvent(new ExecutedEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

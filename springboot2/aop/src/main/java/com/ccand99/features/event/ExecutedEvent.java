package com.ccand99.features.event;

import org.springframework.context.ApplicationEvent;

/**
 * 动作已执行事件
 */
public class ExecutedEvent extends ApplicationEvent {

    public ExecutedEvent(Object source) {
        super(source);
    }


}

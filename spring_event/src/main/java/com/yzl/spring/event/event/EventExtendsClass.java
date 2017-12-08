package com.yzl.spring.event.event;

import org.springframework.context.ApplicationEvent;

public class EventExtendsClass extends ApplicationEvent {

    private String target = " ,everyone";

    public EventExtendsClass(Object source) {
        super(source);
    }

    public EventExtendsClass(Object source, String target) {
        super(source);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}

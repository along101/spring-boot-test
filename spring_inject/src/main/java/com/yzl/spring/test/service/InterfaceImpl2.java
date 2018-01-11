package com.yzl.spring.test.service;

import org.springframework.stereotype.Component;

@Component
public class InterfaceImpl2 implements AnInterface {
    public void doSomething() {
        System.out.println("InterfaceImpl2 do some thing");
    }
}

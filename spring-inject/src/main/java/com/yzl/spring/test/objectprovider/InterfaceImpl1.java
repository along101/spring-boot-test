package com.yzl.spring.test.objectprovider;

import org.springframework.stereotype.Component;

@Component
public class InterfaceImpl1 implements AnInterface {
    public void doSomething() {
        System.out.println("InterfaceImpl1 do some thing");
    }
}

package com.yzl.spring.test.tool.mock.demo;

import org.springframework.stereotype.Component;

/**
 * @author yutu
 * @date 2021-01-30
 */
@Component
public class DemoBean2 implements IDemo {

    @Override
    public String sayHello(String name) {
        return "DemoBean2 say hello to " + name;
    }
}

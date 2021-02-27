package com.yzl.spring.test.tool.mock.demo;

/**
 * @author yutu
 * @date 2021-01-30
 */
public class DemoBean1 implements IDemo {
    @Override
    public String sayHello(String name) {
        return "DemoBean1 say hello to " + name;
    }
}

package com.yzl.spring.test.tool.mock.demo;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yutu
 * @date 2021-01-30
 */
public class DemoManager {

    @Autowired
    private DemoBean1 demoBean1;

    @Autowired
    private DemoBean2 demoBean2;

    @Autowired
    private DemoBean3 demoBean3;

    @Autowired
    private DemoBean4 demoBean4;

    public String sayHello(String name) {
        return demoBean1.sayHello(name) + ", good day.\n" +
                demoBean2.sayHello(name) + ", good day.\n";
    }
}

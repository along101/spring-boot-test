package com.yzl.foo.service;

import com.yzl.foo.api.MyInterface;
import com.yzl.spring.anno.MyService;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@MyService
public class MyServiceBean implements MyInterface {

    public String sayHello(String name) {
        return "hello " + name;
    }
}

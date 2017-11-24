package com.yzl.foo.client;

import com.yzl.foo.api.MyInterface;
import com.yzl.spring.anno.MyReferer;
import org.springframework.stereotype.Component;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Component
public class MyClientBean {

    @MyReferer
    private MyInterface myInterface;

    public String testSay(String name) {
        return myInterface.sayHello(name);
    }
}

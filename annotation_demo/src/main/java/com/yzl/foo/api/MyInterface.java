package com.yzl.foo.api;

import com.yzl.spring.anno.MyClient;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@MyClient("Foo")
public interface MyInterface {

    String sayHello(String name);
}

package com.yzl.foo.api;

import com.yzl.spring.anno.MyClient;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@MyClient(name = "MyInterface", url = "${my.client.url}")
public interface MyInterface {

    String sayHello(String name);
}

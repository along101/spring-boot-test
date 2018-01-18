package com.yzl.alexy.spring.server;

import com.yzl.alexy.spring.annotation.AlexyRestController;
import com.yzl.alexy.spring.demo.HelloRequest;
import com.yzl.alexy.spring.demo.HelloResponse;
import com.yzl.alexy.spring.demo.Simple;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@AlexyRestController
public class SimpleImpl implements Simple {


    @Override
    public String sayHello(@RequestParam("name") String name) {
        return "Hello " + name;
    }

    @Override
    public HelloResponse postHello(@RequestBody HelloRequest request) {
        HelloResponse response = HelloResponse.builder().message("Hello post " + request.getName()).build();
        return response;
    }
}

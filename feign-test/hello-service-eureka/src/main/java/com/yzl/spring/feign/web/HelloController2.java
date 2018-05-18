package com.yzl.spring.feign.web;

import com.yzl.spring.feign.dto.User;
import com.yzl.spring.feign.service.HelloService2;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController2 implements HelloService2 {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public User hello(String name, Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }

}
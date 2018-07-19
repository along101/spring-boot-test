package com.yzl.spring.feign.web;

import com.yzl.spring.feign.dto.User;
import com.yzl.spring.feign.service.HelloService2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController2 implements HelloService2 {

    @Override
    public String hello(@RequestParam("name") String name) {
        return "Hello2 " + name;
    }

    @Override
    public User hello(@RequestParam("name") String name, @RequestHeader("age") Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "Hello2 " + user.getName() + ", " + user.getAge();
    }
}
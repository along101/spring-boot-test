package com.yzl.spring.feign.service;

import com.yzl.spring.feign.dto.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test2")
public interface HelloService2 {

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    User hello(@RequestParam("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    String hello(@RequestBody User user);

}
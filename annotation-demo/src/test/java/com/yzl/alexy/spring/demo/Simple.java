package com.yzl.alexy.spring.demo;

import com.yzl.alexy.spring.annotation.AlexyInterface;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@AlexyInterface
@RequestMapping("/test")
public interface Simple {

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    String sayHello(@RequestParam("name") String name);

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    HelloResponse postHello(@RequestBody HelloRequest request);
}

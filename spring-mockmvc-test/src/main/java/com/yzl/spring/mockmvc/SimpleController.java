package com.yzl.spring.mockmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinzuolong
 */
@RestController
public class SimpleController {

    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String testGet() {
        return "yzl";
    }
}

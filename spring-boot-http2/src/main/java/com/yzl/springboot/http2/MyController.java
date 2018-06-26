package com.yzl.springboot.http2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinzuolong
 */
@RestController
public class MyController {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/test")
    public Map<String, String> getTestResult() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "2");
        map.put("2", "3");
        return map;
    }
}

package com.yzl.spring.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yinzuolong on 2017/3/30.
 */
@RestController
public class MyController {
    //
//    @Autowired
//    private MetricWriter metricWriter;
    @Autowired
    private GaugeService gaugeService;
    private boolean isHealth = true;

    @RequestMapping("/s1")
    public String shutdown() {
        isHealth = false;
        return "shutdown";
    }

    @RequestMapping("/test")
    public Object test() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", "2");
        return map;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Object testPost(@RequestBody Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("123", "345");
        return map;
    }

    @RequestMapping("/s2")
    public String startup() {
        isHealth = true;
        return "startup";
    }

    @RequestMapping("/hello")
    public String hello() {
        gaugeService.submit("test", 2);
//        metricWriter.increment(new Delta<Number>("metricWriter", 4));
        return "hello world";
    }

    public boolean isHealth() {
        return isHealth;
    }

    public void setHealth(boolean health) {
        isHealth = health;
    }


    @GetMapping("/get1")
    public Test get1() {
        Test t = new Test();
        t.a = "12";
        return t;
    }

    public static class Test {
        private String a;
        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

}

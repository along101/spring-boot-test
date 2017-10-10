package com.ppdai.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

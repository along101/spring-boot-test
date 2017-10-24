package com.yzl.spring.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义监控检查器
 * 在浏览器分别运行/s1、/s2，查看http://localhost:8191/health
 * Created by yinzuolong on 2017/3/30.
 */
@Component
public class MyHealth implements HealthIndicator {
    @Autowired
    private MyController controller;

    @Override
    public Health health() {
        Health health;
        if (controller.isHealth()) {
            health = Health.up().withDetail("myHealth", "ok").build();
        } else {
            health = Health.down().withDetail("myHealth", "wrong").build();
        }
        return health;
    }
}

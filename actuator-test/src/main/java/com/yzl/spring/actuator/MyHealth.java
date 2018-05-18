package com.yzl.spring.actuator;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        A a = new A();
        a.setA("ppdai");
        a.setB("ppdai".getBytes());

        String json = objectMapper.writeValueAsString(a);

        System.out.println(json);

        A newA = objectMapper.readValue(json, A.class);
        System.out.println(new String(newA.getB()));
    }

    static class A {
        byte[] b;
        String a;

        public byte[] getB() {
            return b;
        }

        public void setB(byte[] b) {
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }
}

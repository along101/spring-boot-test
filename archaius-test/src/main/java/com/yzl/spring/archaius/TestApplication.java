package com.yzl.spring.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.archaius.ArchaiusAutoConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TestApplication {

    @Autowired
    ConfigurableEnvironment env;

    @Autowired
    ArchaiusAutoConfiguration archaiusAutoConfiguration;

    private static final DynamicStringProperty archaiusTest =
            DynamicPropertyFactory.getInstance().getStringProperty("archaius.test", "test1");

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TestApplication.class, args);
        Thread.sleep(50000 * 1000);
    }

    @PostConstruct
    public void init() {
        System.out.println("env config: " + env.getProperty("archaius.test"));
        System.out.println("archaius config: " + archaiusTest.get());

        MutablePropertySources sources = env.getPropertySources();
        Map<String, Object> config = new HashMap<>();
        config.put("archaius.test", "map change");
        sources.addFirst(new MapPropertySource("myMap", config));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        Thread.sleep(2 * 1000);
                        System.out.println("archaius config: " + archaiusTest.get());
                        i++;
                        config.put("archaius.test", "map change " + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

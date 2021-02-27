package com.yzl.spring.test.tool.mock.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yutu
 * @date 2021-01-30
 */
@Configuration
public class DemoConfig {

    @Bean
    public DemoBean1 demoBean1() {
        return new DemoBean1();
    }

    @Bean
    public DemoManager demoManager() {
        return new DemoManager();
    }

    @Bean
    public DemoFactory demoBean5() {
        return new DemoFactory();
    }
}

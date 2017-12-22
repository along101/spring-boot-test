package com.yzl.alexy.spring.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AlexyClientAutoConfiguration {

    @Autowired
    private Environment environment;

}

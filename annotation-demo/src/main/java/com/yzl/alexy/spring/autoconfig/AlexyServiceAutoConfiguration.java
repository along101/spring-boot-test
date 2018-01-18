package com.yzl.alexy.spring.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import({AlexyServiceProcessor.class})
public class AlexyServiceAutoConfiguration {
    @Autowired
    private Environment env;


}

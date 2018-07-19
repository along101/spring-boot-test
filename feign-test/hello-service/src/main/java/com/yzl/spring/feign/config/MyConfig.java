package com.yzl.spring.feign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yinzuolong
 */
@Configuration
@Import(HandlerMappingPostProcessor.class)
public class MyConfig {


}

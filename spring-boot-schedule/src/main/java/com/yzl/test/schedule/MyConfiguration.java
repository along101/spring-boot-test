package com.yzl.test.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author yutu
 * @date 2018/9/27
 */
@Configuration
public class MyConfiguration {

    @Bean
    public ThreadPoolTaskScheduler createScheduler(){
        return  new ThreadPoolTaskScheduler();
    }
}

package com.yzl.spring.event.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 在 /META-INF/spring.factories 中声明ApplicationListener,回会比声明成spring bean 多处理一些时间,比如:
 * ApplicationStartedEvent,
 * ApplicationEnvironmentPreparedEvent ,
 * ApplicationPreparedEvent...
 *
 *
 * @author zhangchengxi
 * Date 2019/2/2
 */
public class ListenerWithSpringFactories implements ApplicationListener {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ListenerWithSpringFactories:"+event.getClass().getName());
    }
}

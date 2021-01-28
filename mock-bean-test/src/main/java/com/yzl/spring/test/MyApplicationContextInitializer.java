package com.yzl.spring.test;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 这个类在spring.factories中配置，自定义启动方式，可以操作初始化的applicationContext
 * 比如增加一个BeanFactoryPostProcessor，又可以去注册bean，也可以去直接操作操作BeanFactory
 *
 * @author yutu
 * @date 2021-01-29
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addBeanFactoryPostProcessor(new ContextInitializePostProcessor());
    }
}

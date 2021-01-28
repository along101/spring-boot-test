package com.yzl.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        System.out.println("这是BeanPostProcessor实现类构造器！！");
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if ("person".equalsIgnoreCase(beanName)) {
            System.out.println("BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if ("person".equalsIgnoreCase(beanName)) {
            System.out.println("BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！");
        }
        return bean;
    }
}
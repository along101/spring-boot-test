package com.yzl.spring.anno;

import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 参考motan源码com.weibo.api.motan.config.springsupport
 */
public class MyBeanPostProcessor implements DisposableBean, BeanFactoryPostProcessor, BeanPostProcessor, BeanFactoryAware, Ordered {

    private BeanFactory beanFactory;

    private final Map<ServiceConfig<?>, String> serviceConfigs = new ConcurrentHashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void registerMyService() {
        BeanFactory beanFactory = this.beanFactory;
        while (beanFactory != null) {
            if (beanFactory instanceof ListableBeanFactory) {
                registerMyService((ListableBeanFactory) beanFactory);
            }
            beanFactory = (beanFactory instanceof HierarchicalBeanFactory
                    ? ((HierarchicalBeanFactory) beanFactory).getParentBeanFactory()
                    : null);
        }
    }

    private void registerMyService(ListableBeanFactory beanFactory) {
        Map<String, Object> beans = beanFactory
                .getBeansWithAnnotation(JsonComponent.class);
        for (Object bean : beans.values()) {
            //这里获取到注册的bean
            System.out.println(bean);
        }
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //这里可以扫描@MyService，不这么干，在postProcessAfterInitialization中处理
    }

    /**
     * 处理MyReferer
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            MyReferer annotation = field.getAnnotation(MyReferer.class);
            if (annotation == null) {
                continue;
            }

            //获取字段的代理对象
            Object proxy = beanFactory.getBean(field.getType());
            if (proxy instanceof AopProxy) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, proxy);
            }

        }
        return bean;
    }

    /**
     * 处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        if (AopUtils.isAopProxy(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        MyService myService = AnnotationUtils.findAnnotation(clazz, MyService.class);
        if (myService != null) {
            ServiceConfig serviceConfig = new ServiceConfig();
            Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(myService);
            serviceConfig.setModule(myService.module());
            serviceConfig.setGroup(myService.group());
            serviceConfig.setVersion(myService.version());
            serviceConfig.setActives(myService.actives());
            serviceConfig.setRequestTimeout(myService.requestTimeout());
            serviceConfig.setRetries(myService.retries());

            //TODO 服务注册
            this.serviceConfigs.put(serviceConfig, beanName);
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
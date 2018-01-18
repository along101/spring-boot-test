package com.yzl.alexy.spring.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AlexyClientFactoryBean implements FactoryBean<Object>,
        ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Class<?> interfaceClass;

    @Override
    public Object getObject() throws Exception {
        //TODO 创建代理
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}

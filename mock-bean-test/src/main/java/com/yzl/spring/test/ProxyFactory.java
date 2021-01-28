package com.yzl.spring.test;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author yutu
 * @date 2021-01-28
 */
public class ProxyFactory implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        InvocationHandler h = (proxy, method, args) -> null;
        Class<?>[] interfaces = new Class[]{Interface1.class};
        return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), interfaces, h);
    }

    @Override
    public Class<?> getObjectType() {
        return Interface1.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

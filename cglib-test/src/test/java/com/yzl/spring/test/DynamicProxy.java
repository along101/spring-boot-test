package com.yzl.spring.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yutu
 * @date 2021-01-28
 */
public class DynamicProxy implements MethodInterceptor {

    //真实对象
    Object targetObject;

    public Object getProxyObject(Object object) {
        this.targetObject = object;
        //增强器，动态代码生成器
        Enhancer enhancer = new Enhancer();
        //回调方法
        enhancer.setCallback(this);
        //设置生成类的父类类型
        enhancer.setSuperclass(targetObject.getClass());
        //动态生成字节码并返回代理对象
        return enhancer.create();
    }

    //拦截方法
    @Override
    public Object intercept(Object arg0, Method arg1, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Hello,I am the weaved method before");

        Object result = methodProxy.invoke(targetObject, args);

        System.out.println("Hello,I am the weaved method after");

        return result;
    }

}


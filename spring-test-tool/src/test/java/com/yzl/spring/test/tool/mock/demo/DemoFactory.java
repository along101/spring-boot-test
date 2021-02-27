package com.yzl.spring.test.tool.mock.demo;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author yutu
 * @date 2021-01-30
 */
public class DemoFactory implements FactoryBean<DemoBean5> {

    @Override
    public DemoBean5 getObject() throws Exception {
        return new DemoBean5();
    }

    @Override
    public Class<?> getObjectType() {
        return DemoBean5.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

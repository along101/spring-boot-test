package com.yzl.spring.test.tool.mock;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * @author yutu
 * @date 2021-01-26
 */
@Slf4j
public class MyMockBeanFactory implements FactoryBean, BeanNameAware, BeanClassLoaderAware {

    private ClassLoader classLoader;

    private String beanName;

    @Setter
    private String beanClass;

    private Class<?> clazz;

    @Override
    public Object getObject() {
        try {
            Object obj = Mockito.mock(getObjectType());
            log.info("mock bean {} {}", beanClass, beanName);
            return obj;
        } catch (Exception e) {
            log.error("error mock bean {} {}", beanClass, beanName, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> getObjectType() {
        if (clazz == null && !StringUtils.isEmpty(beanClass)) {
            try {
                clazz = ClassUtils.forName(beanClass, classLoader);
            } catch (ClassNotFoundException e) {
                log.error("load class error {}", beanClass, e);
            }
        }
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}

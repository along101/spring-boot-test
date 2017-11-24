package com.yzl.spring.anno;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by yinzuolong on 2017/11/24.
 * 参考 org.springframework.cloud.netflix.feign.FeignClientsRegistrar
 * <p>
 * TODO 扫描@EnableMyClient中的basePackage中被@MyClient注解的接口，生成代理对象注入到springcontext中
 */
public class MyClientsRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanClassLoaderAware {
    //TODO 参考feign
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    }
}

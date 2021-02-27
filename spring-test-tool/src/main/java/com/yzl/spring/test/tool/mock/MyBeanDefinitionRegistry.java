package com.yzl.spring.test.tool.mock;

import com.yzl.spring.test.tool.performance.MyBeanInitRecorder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * bean定义注册器
 *
 * @author yutu
 * @date 2021-01-29
 */
@Slf4j
public class MyBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //注册bean启动记录器
        MyBeanInitRecorder.getOrRegistryBeanDefinition(registry);

        //注册mock加载器
        MyMockPostProcessor.getOrRegistryBeanDefinition(registry);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

}

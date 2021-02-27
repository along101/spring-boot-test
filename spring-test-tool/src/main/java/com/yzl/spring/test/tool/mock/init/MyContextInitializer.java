package com.yzl.spring.test.tool.mock.init;

import com.yzl.spring.test.tool.mock.MyBeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 自定义spring boot Context 启动
 * 增加BeanDefinitionRegistryPostProcessor
 *
 * @author yutu
 * @date 2021-01-29
 */
public class MyContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addBeanFactoryPostProcessor(new MyBeanDefinitionRegistry());
    }
}

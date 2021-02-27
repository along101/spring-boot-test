package com.yzl.spring.test.tool.performance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yutu
 * @date 2021-01-30
 */
@Slf4j
public class MyBeanInitRecorder implements BeanPostProcessor, ApplicationListener<ApplicationEvent>, Ordered {

    public static BeanDefinition getOrRegistryBeanDefinition(BeanDefinitionRegistry registry) {
        String beanName = MyBeanInitRecorder.class.getName();
        if (registry.containsBeanDefinition(beanName)) {
            return registry.getBeanDefinition(beanName);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyBeanInitRecorder.class.getName());
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        return builder.getBeanDefinition();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String key = bean.getClass().getSimpleName() + "#" + beanName;
        MyTimeRecorderUtil.recordStart(key);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String key = bean.getClass().getSimpleName() + "#" + beanName;
        MyTimeRecorderUtil.recordEnd(key);
        return bean;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (!(event instanceof ContextRefreshedEvent) && !(event instanceof ApplicationFailedEvent)) {
            return;
        }
        log.warn("{} \n===spring-init-result===", event.getClass());
        List<MyTimeRecorderUtil.Record> list = new ArrayList<>(MyTimeRecorderUtil.records.values());
        list.sort((r1, r2) -> (int) (r1.getCost() - r2.getCost()));
        if (list.size() > 20) {
            list = list.subList(list.size() - 20, list.size());
        }
        Collections.reverse(list);
        String s = list.stream().map(r -> r.getKey() + " cost: " + r.getCost()).collect(Collectors.joining("\n"));
        log.warn("\n===most-init-cost-of-bean===\n{}", s);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}

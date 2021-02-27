package com.yzl.spring.test.tool.mock.init;

import com.yzl.spring.test.tool.mock.MyBeanDefinitionRegistry;
import com.yzl.spring.test.tool.mock.MyMockPostProcessor;
import com.yzl.spring.test.tool.mock.annotation.MyMockBean;
import com.yzl.spring.test.tool.mock.annotation.MyMockExclude;
import com.yzl.spring.test.tool.mock.annotation.MyMockRule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author yutu
 * @date 2021-01-31
 */
public class MyTestContextCustomer implements ContextCustomizer {
    @Setter
    @Getter
    private Set<MyMockRule> myMockRules;

    @Setter
    @Getter
    private Set<MyMockBean> myMockBeans;

    @Setter
    @Getter
    private Set<MyMockExclude> myMockExcludes;

    @SuppressWarnings("unchecked")
    @Override
    public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
        context.addBeanFactoryPostProcessor(new MyBeanDefinitionRegistry());

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinition definition = MyMockPostProcessor.getOrRegistryBeanDefinition((BeanDefinitionRegistry) beanFactory);
            Set<MyMockRule> myMocks = (Set<MyMockRule>) definition.getPropertyValues().get("myMocks");
            if (CollectionUtils.isEmpty(myMocks)) {
                myMocks = new LinkedHashSet<>();
                definition.getPropertyValues().add("myMocks", myMocks);
            }
            if (!CollectionUtils.isEmpty(this.myMockRules)) {
                myMocks.addAll(this.myMockRules);
            }

            Set<MyMockBean> myMockBeans = (Set<MyMockBean>) definition.getPropertyValues().get("myMockBeans");
            if (CollectionUtils.isEmpty(myMockBeans)) {
                myMockBeans = new LinkedHashSet<>();
                definition.getPropertyValues().add("myMockBeans", myMockBeans);
            }
            if (!CollectionUtils.isEmpty(this.myMockBeans)) {
                myMockBeans.addAll(this.myMockBeans);
            }

            Set<MyMockExclude> myMockExcludes = (Set<MyMockExclude>) definition.getPropertyValues().get("myMockExcludes");
            if (CollectionUtils.isEmpty(myMockExcludes)) {
                myMockExcludes = new LinkedHashSet<>();
                definition.getPropertyValues().add("myMockExcludes", myMockExcludes);
            }
            if (!CollectionUtils.isEmpty(this.myMockExcludes)) {
                myMockExcludes.addAll(this.myMockExcludes);
            }
        }
    }

}

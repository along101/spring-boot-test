package com.yzl.spring.test.tool.mock.init;

import com.yzl.spring.test.tool.mock.annotation.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

import java.util.List;
import java.util.Set;

/**
 * 测试启动类，在spring.factories配置
 * 测试类本上不在spring context容器中，通过bean工厂读取不到，所有用这个自定义工厂配置
 * 启动spring-boot时，会加载这个，就可以将测试类上的注解读出来
 *
 * @author yutu
 * @date 2021-01-31
 */
public class MyTestContextCustomizerFactory implements ContextCustomizerFactory {
    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass,
                                                     List<ContextConfigurationAttributes> configAttributes) {
        MyTestContextCustomer contextCustomizer = new MyTestContextCustomer();

        Set<MyMockRule> myMockRules = AnnotationUtils.getRepeatableAnnotations(testClass, MyMockRule.class, MyMockRules.class);
        contextCustomizer.setMyMockRules(myMockRules);

        Set<MyMockBean> myMockBeans = AnnotationUtils.getRepeatableAnnotations(testClass, MyMockBean.class, MyMockBeans.class);
        contextCustomizer.setMyMockBeans(myMockBeans);

        Set<MyMockExclude> myMockExcludes = AnnotationUtils.getRepeatableAnnotations(testClass, MyMockExclude.class, MyMockExcludes.class);
        contextCustomizer.setMyMockExcludes(myMockExcludes);
        return contextCustomizer;
    }

}

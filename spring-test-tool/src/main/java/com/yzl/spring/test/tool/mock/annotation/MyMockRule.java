package com.yzl.spring.test.tool.mock.annotation;

import java.lang.annotation.*;

/**
 * 配置需要mock的bean
 * 只要满足任意一个条件，就会mock需要的bean
 *
 * @author yutu
 * @date 2021-01-30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(MyMockRules.class)
public @interface MyMockRule {

    boolean enabled() default true;

    String enabledStr() default "true";

    /**
     * bean的名称，spring context中，每个bean只有唯一的名称
     *
     * @return
     */
    String[] beanName() default {};

    /**
     * bean的类，创建bean时声明的类
     *
     * @return
     */
    Class<?>[] beanClass() default {};

    /**
     * 创建bean工厂的class
     * 如果是@Component注解注入的Bean，则为空
     * 如果是@Bean方法创建的Bean，则为该配置类
     * 如果是@HSFConsumer创建的Bean，则为该配置类
     *
     * @return
     */
    Class<?>[] factoryClass() default {};

    /**
     * 创建bean的注解，例如@Component、@Bean、@HSFConsumer
     * 注解是一个数组，可能有多个
     *
     * @return
     */
    Class<?>[] annotation() default {};

    /**
     * 可以通过beanName、beanClass、factoryClass、annotation写ql表达式
     * 表达式结果为true，则需要mock改bean，例如：
     * beanClass like 'com.yzl.spring.test.tool.mock%'
     *
     * @return
     */
    String expression() default "";

}

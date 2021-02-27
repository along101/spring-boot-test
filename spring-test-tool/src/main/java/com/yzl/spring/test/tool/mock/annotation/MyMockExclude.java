package com.yzl.spring.test.tool.mock.annotation;

import java.lang.annotation.*;

/**
 * 配置mock的排除项
 * 先根据@MyMock注解配置出需要的mock
 * 再根据此注解，排除掉不需要的mock
 * 主要作用是在父类上将所有外部依赖mock掉，在具体单测中，排除掉指定的mock
 *
 * @author yutu
 * @date 2021-01-31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(MyMockExcludes.class)
public @interface MyMockExclude {

    /**
     * @return
     * @see MyMockRule#beanName()
     */
    String[] beanName() default {};

    /**
     * @return
     * @see MyMockRule#beanClass()
     */
    Class<?>[] beanClass() default {};

    /**
     * @return
     * @see MyMockRule#factoryClass()
     */
    Class<?>[] factoryClass() default {};

    /**
     * @return
     * @see MyMockRule#annotation()
     */
    Class<?>[] annotation() default {};

    /**
     * @return
     * @see MyMockRule#expression()
     */
    String expression() default "";
}

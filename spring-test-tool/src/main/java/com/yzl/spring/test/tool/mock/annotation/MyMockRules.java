package com.yzl.spring.test.tool.mock.annotation;

import java.lang.annotation.*;

/**
 * @author yutu
 * @date 2021-01-30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMockRules {

    MyMockRule[] value();

}

package com.yzl.spring.test.tool.mock.annotation;

import java.lang.annotation.*;

/**
 * @author yutu
 * @date 2021-02-25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyMockBeans {

    MyMockBean[] value();
}

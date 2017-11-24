package com.yzl.spring.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyClient {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String url() default "";

    Class<?> fallback() default void.class;
}

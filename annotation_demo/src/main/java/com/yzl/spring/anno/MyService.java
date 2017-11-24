package com.yzl.spring.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
    String value() default "";
}

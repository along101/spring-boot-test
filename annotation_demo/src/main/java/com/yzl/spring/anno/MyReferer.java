package com.yzl.spring.anno;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MyReferer {
}

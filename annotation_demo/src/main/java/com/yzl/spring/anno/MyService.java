package com.yzl.spring.anno;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
    // bean名字
    String value() default "";

    // 模块名称
    String module() default "";

    // 分组
    String group() default "";

    // 服务版本
    String version() default "";

    // 最大并发调用
    int actives() default 0;

    // 请求超时时间
    int requestTimeout() default 0;

    // 重试次数
    int retries() default 0;
}

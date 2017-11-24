package com.yzl.spring.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MyClientsRegistrar.class)
public @interface EnableMyClient {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}

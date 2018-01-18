package com.yzl.alexy.spring.annotation;

import com.yzl.alexy.spring.autoconfig.AlexyClientReferProcessor;
import com.yzl.alexy.spring.autoconfig.AlexyClientsRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by yinzuolong on 2017/12/2.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AlexyClientsRegistrar.class, AlexyClientReferProcessor.class})
public @interface EnableAlexyClients {

    @AliasFor("basePackageClasses")
    Class<?>[] value() default {};

    String[] basePackages() default {};

    @AliasFor("value")
    Class<?>[] basePackageClasses() default {};
}

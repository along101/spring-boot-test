package com.yzl.spi;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Spi {

    Scope scope() default Scope.PROTOTYPE;

}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * 包扫描测试
 *
 * @author yinzuolong
 */
public class ClassPathScanningCandidateComponentProviderTest {

    @Test
    public void test() {
        // 根据控制台信息可以看到,内部实现是通过PathMatchingResourcePatternResolver读取所有classpath*:com/yzl/spring/utils/**/*.class文件,再进行过滤
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyAnno.class));

        Set<BeanDefinition> b = scanner.findCandidateComponents(ClassPathScanningCandidateComponentProviderTest.class.getPackage().getName());

        System.out.println(b);
    }

    @MyAnno
    public static class A {

    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface MyAnno {

    }
}

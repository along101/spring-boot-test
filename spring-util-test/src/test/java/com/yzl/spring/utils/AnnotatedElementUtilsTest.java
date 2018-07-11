package com.yzl.spring.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * 注解工具,在AnnotationUtils基础上增加了更强大的方法
 *
 * @author yinzuolong
 */
public class AnnotatedElementUtilsTest {

    @Test
    public void test() {

        //获取注解上的注解的属性
        Method method = ReflectionUtils.findMethod(AnnotationUtilsTest.A.class, "getUserNameById", Integer.TYPE);
        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.findMergedAnnotationAttributes(method, RequestMapping.class, false, false);
        RequestMethod[] requestMethods = (RequestMethod[]) annotationAttributes.get("method");
        Assert.assertEquals(requestMethods[0], RequestMethod.GET);

        //获取注解上的注解，原理是获取注解上的属性，然后通过动态代理构造一个注解对象
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        Assert.assertEquals(requestMapping.method()[0], RequestMethod.GET);

    }

    @RestController
    public static class A {

        @GetMapping(path = "/test")
        public String getUserNameById(int id) {
            return "name";
        }
    }
}

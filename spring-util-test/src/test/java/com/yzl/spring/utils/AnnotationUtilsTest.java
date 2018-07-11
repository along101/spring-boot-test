package com.yzl.spring.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * Created by yinzuolong on 2017/11/24.
 */
public class AnnotationUtilsTest {

    @Test
    public void test() {
        RestController restController = AnnotationUtils.findAnnotation(A.class, RestController.class);
        Assert.assertNotNull(restController);

        Method method = ReflectionUtils.findMethod(A.class, "getUserNameById", Integer.TYPE);

        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        Assert.assertNotNull(requestMapping);

        ResponseBody responseBody = AnnotationUtils.getAnnotation(restController, ResponseBody.class);
        Assert.assertNotNull(responseBody);
    }


    @RestController
    public static class A {

        @RequestMapping(method = RequestMethod.GET, path = "/test")
        public String getUserNameById(int id) {
            return "name";
        }
    }
}

package com.yzl.spring.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author yinzuolong
 */
//TODO
public class ReflectionUtilsTest {

    @Test
    public void getPrivateValue() {
        Cat cat = new Cat("tomcat");
        Field field = ReflectionUtils.findField(Cat.class, "name");
        ReflectionUtils.makeAccessible(field);
        String name = (String) ReflectionUtils.getField(field, cat);
        Assert.assertEquals("tomcat", name);
    }

    static class Cat {

        private String name;

        public Cat(String name) {
            this.name = name;
        }
    }
}

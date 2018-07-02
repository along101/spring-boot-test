package com.yzl.spring.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class GenericTest {

    private List<String> forField;

    @Test
    public void testForField() {
        Field field = ReflectionUtils.findField(GenericTest.class, "forField");
        ResolvableType resolvableType = ResolvableType.forField(field);
        Assert.assertEquals(String.class, resolvableType.getGeneric(0).getRawClass());
    }
}

package com.yzl.spring.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.TypeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型工具，主要是判断类型的继承关系
 *
 * @author yinzuolong
 */
public class TypeUtilsTest {

    @Test
    public void test() {
        Assert.assertTrue(TypeUtils.isAssignable(Map.class, HashMap.class));

        Assert.assertFalse(TypeUtils.isAssignable(Map.class, String.class));
    }
}

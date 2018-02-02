package com.yzl.test.java8.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class ObjectsTest {

    @Test
    public void test() {
        String a = null;
        try {
            Objects.requireNonNull(a, "parameter can not be null.");
        } catch (Exception e) {
            Assert.assertNotNull(e);
            e.printStackTrace();
            return;
        }
        String s = Objects.toString(a, "default");
        Assert.assertEquals(s, "default");

    }
}

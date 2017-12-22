package com.yzl.spi.test;

import com.yzl.spi.BinderSupporter;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BinderSupporterTest {

    @Test
    public void testGetAllSuper() {
        Set<Type> set = BinderSupporter.getAllSuper(A.class);
        System.out.println(set);
    }

    public static class A extends HashMap<List<String>, Map<Integer, List<String>>> {
    }
}

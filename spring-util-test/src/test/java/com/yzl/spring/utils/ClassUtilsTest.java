package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Class工具类,对class的一些反射获取,功能很强大
 *
 * @author yinzuolong
 */
public class ClassUtilsTest {

    @Test
    public void test() {
        Method method = ClassUtils.getMethodIfAvailable(String.class, "compareTo", String.class);
        System.out.println(ClassUtils.getQualifiedMethodName(method));
    }
}

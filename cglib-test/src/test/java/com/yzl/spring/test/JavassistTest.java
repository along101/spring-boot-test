package com.yzl.spring.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author yutu
 * @date 2021-01-28
 */
public class JavassistTest {
    @Test
    public void test() throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(RealClass.class.getName());

        cc.setName("MyTest");

        CtMethod ctInner = cc.getDeclaredMethod("inner");
        ctInner.setBody("System.out.println(\"inner 修改了\");");

        CtMethod ctHello = cc.getDeclaredMethod("hello");
        ctHello.insertBefore("System.out.println(\"hello 之前\");");

        Class myTestClass = cc.toClass();
        Object myTest = myTestClass.newInstance();
        Method hello = ReflectionUtils.findMethod(myTestClass, "hello");
        ReflectionUtils.invokeMethod(hello, myTest);
    }
}

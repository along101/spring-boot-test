package com.yzl.spring.test;

import org.junit.Test;
import org.springframework.asm.Type;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InterfaceMaker;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author yutu
 * @date 2021-01-28
 */
public class CglibTest {

    @Test
    public void test1() throws Exception {

        // 设置类成员属性
        HashMap propertyMap = new HashMap();

        propertyMap.put("id", Class.forName("java.lang.Integer"));

        propertyMap.put("name", Class.forName("java.lang.String"));

        propertyMap.put("address", Class.forName("java.lang.String"));

        // 生成动态 Bean
        CglibBean bean = new CglibBean(propertyMap);

        // 给 Bean 设置值
        bean.setValue("id", new Integer(123));

        bean.setValue("name", "454");

        bean.setValue("address", "789");

        // 从 Bean 中获取值，当然了获得值的类型是 Object

        System.out.println("  >> id      = " + bean.getValue("id"));

        System.out.println("  >> name    = " + bean.getValue("name"));

        System.out.println("  >> address = " + bean.getValue("address"));

        // 获得bean的实体
        Object object = bean.getObject();

        // 通过反射查看所有方法名
        Class clazz = object.getClass();
        System.out.println(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }
    }

    @Test
    public void test2() {
        RealClass realObject = (RealClass) new DynamicProxy().getProxyObject(new RealClass());
        realObject.hello();
    }

    @Test
    public void test3() {

        InterfaceMaker im = new InterfaceMaker();
        im.add(new Signature("setCreatedAt", Type.VOID_TYPE,
                new Type[] { Type.getType(String.class) }), null);


        Class myInterface = im.create();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealClass.class);
        enhancer.setInterfaces(new Class[] { myInterface });
    }
}

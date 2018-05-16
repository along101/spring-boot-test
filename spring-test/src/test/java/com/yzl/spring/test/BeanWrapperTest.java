package com.yzl.spring.test;

import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.convert.TypeDescriptor;

import java.beans.PropertyDescriptor;

/**
 * @author yinzuolong
 */
public class BeanWrapperTest {

    @Test
    public void testResolve() {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(Foo.class);
        beanWrapper.setAutoGrowNestedPaths(true);

        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.addPropertyValue("name", "yzl");
        pvs.addPropertyValue("bar.name", "yzl");
        pvs.addPropertyValue("subList", new String[]{"1","2"});
        pvs.addPropertyValue("map1[a]", "a");
        pvs.addPropertyValue("map1[b]", "b");
        pvs.addPropertyValue("barMap[bar1].name", "bar1");
        pvs.addPropertyValue("barMap[bar1].subList[0]", "1");
        beanWrapper.setPropertyValues(pvs);

        Foo foo = (Foo) beanWrapper.getWrappedInstance();

        System.out.println(foo);


        MyBeanWrapper myBeanWrapper = new MyBeanWrapper();
        myBeanWrapper.setWrappedInstance(foo);
        myBeanWrapper.getValues();

        System.out.println(myBeanWrapper);
    }
}

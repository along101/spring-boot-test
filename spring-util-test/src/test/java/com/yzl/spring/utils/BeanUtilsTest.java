package com.yzl.spring.utils;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * Bean工具类,比较好用的是copyProperties
 *
 * @author yinzuolong
 */
public class BeanUtilsTest {

    //拷贝对象属性
    @Test
    public void copyProperties() throws Exception {
        T1 t1 = new T1();
        t1.setA(1);
        T2 t2 = new T2();
        t2.setText("test");

        BeanUtils.copyProperties(t1, t2);
        Assert.assertEquals(1, t2.a);
        Assert.assertNull(t2.text);
    }


    @Data
    public static class T1 {
        private int a;
        private String text;
    }

    @Data
    private static class T2 {
        private int a;
        private String text;
    }
}

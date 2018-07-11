package com.yzl.spring.utils;

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
        T1 t1 = new T1(1);
        T2 t2 = new T2(2);

        BeanUtils.copyProperties(t1, t2);
        Assert.assertEquals(1, t2.a);
    }


    public static class T1 {
        private int a;

        public T1(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    private static class T2 {
        private int a;

        public T2(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }
}

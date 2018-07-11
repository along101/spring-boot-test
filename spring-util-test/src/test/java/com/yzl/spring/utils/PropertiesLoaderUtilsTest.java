package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author yinzuolong
 */
public class PropertiesLoaderUtilsTest {

    @Test
    public void test() throws IOException {
        //这里只能将属性合并,多个属性文件中相同的key,只能取其中一个
        Properties p = PropertiesLoaderUtils.loadAllProperties("META-INF/spring.factories");
        System.out.println(p.getProperty(EnableAutoConfiguration.class.getName()));

        //这里是将所有的key的值合并起来,原理是通过ClassLoader.getResources获取所需要的resource，然后再合并
        List<String> s = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, PropertiesLoaderUtilsTest.class.getClassLoader());
        System.out.println(s);
    }
}

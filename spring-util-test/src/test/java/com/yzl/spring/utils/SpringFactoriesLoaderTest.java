package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * SpringFactoriesLoader 读取 META-INF/spring.fatories配置中的信息
 * @author yinzuolong
 */
public class SpringFactoriesLoaderTest {

    @Test
    public void test() {
        List<String> factories = SpringFactoriesLoader.loadFactoryNames(PropertySourceLoader.class, SpringFactoriesLoaderTest.class.getClassLoader());
        System.out.println(factories);
    }
}

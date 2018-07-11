package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.boot.bind.PropertySourceUtils;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * spring boot中获取前缀的所有属性
 *
 * @author yinzuolong
 */
public class PropertySourceUtilsTest {

    @Test
    public void test() {

        Map<String, Object> map = new HashMap<>();
        map.put("m.a1", "a1");
        map.put("n.a1", "a1");
        map.put("along101.a1", "a1");
        map.put("along101.a1.b1", "b1");
        map.put("along101.a1.b1.c1", "c1");
        map.put("along101.a1.b2.c1", "b2");
        map.put("along101.a1.b2.c2", "c2");
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(new MapPropertySource("t1", map));

        Map<String, Object> subMap = PropertySourceUtils.getSubProperties(propertySources, "along101.");
        System.out.println(subMap);
    }
}

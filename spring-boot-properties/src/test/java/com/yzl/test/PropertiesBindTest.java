package com.yzl.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.bind.PropertySourcesBinder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yinzuolong
 */
public class PropertiesBindTest {

    @Test
    public void testBind() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("my.properties"));
        Map<String, Object> map = properties.stringPropertyNames().stream().collect(Collectors.toMap(Function.identity(), properties::getProperty));

        MapPropertySource mapPropertySource = new MapPropertySource("myMap", map);
        PropertySourcesBinder propertySourcesBinder = new PropertySourcesBinder(mapPropertySource);
        MyProperties config = new MyProperties();
        propertySourcesBinder.bindTo("yzl", config);

        Assert.assertEquals("yzl123", config.getStr());
        Assert.assertEquals("test3", config.getStrList().get(2));
        Assert.assertEquals(123, (int) config.getSubProperties().get(0).getSubInteger());
        Assert.assertEquals("test11", config.getSubMapStr().get("key1.key1.key1"));
        Assert.assertEquals("test22", config.getSubMap().get("key1").getSubStr());
        Assert.assertEquals("test13123", config.getSubMap().get("com.yzl.test1.Test").getSubStr());

    }
}

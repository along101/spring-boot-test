package com.yzl.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.bind.PropertySourcesBinder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.URL;
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
        URL resources = Thread.currentThread().getContextClassLoader().getResource("my.properties");
        Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(resources));
        Map<String, String> map = properties.stringPropertyNames().stream().collect(Collectors.toMap(Function.identity(), properties::getProperty));

        MapPropertySource mapPropertySource = new MapPropertySource("myMap", (Map) map);
        PropertySourcesBinder propertySourcesBinder = new PropertySourcesBinder(mapPropertySource);
        MyProperties config = new MyProperties();
        propertySourcesBinder.bindTo("yzl", config);

        Assert.assertEquals("yzl123", config.getStr());
        Assert.assertEquals("test3", config.getStrList().get(2));
        Assert.assertTrue(123 == config.getSubProperties().get(0).getSubInteger());
        Assert.assertEquals("test11" , config.getSubMapStr().get("key1.key1.key1"));
        Assert.assertEquals("test22" , config.getSubMap().get("key1").getSubStr());
        Assert.assertEquals("test13123" , config.getSubMap().get("com.yzl.test1.Test").getSubStr());



    }
}

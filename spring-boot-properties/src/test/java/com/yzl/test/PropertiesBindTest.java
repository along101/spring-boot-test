package com.yzl.test;

import org.junit.Test;
import org.springframework.boot.bind.PropertySourcesBinder;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinzuolong
 */
public class PropertiesBindTest {

    @Test
    public void testBind() {
        Map<String, String> map = new HashMap<>();
        map.put("yzl.str", "yzl123");
        map.put("yzl.integer", "123");
        map.put("yzl.strList[0]", "test1");
        map.put("yzl.strList[1]", "test2");
        map.put("yzl.strList[2]", "test3");
        map.put("yzl.subProperties[0].subStr", "test3");
        map.put("yzl.subProperties[0].subInteger", "123");
        map.put("yzl.subMapStr.key1", "test11");
        map.put("yzl.subMapStr.key2", "test22");
        map.put("yzl.subMapStr.key1.key1.key1", "test11");
        map.put("yzl.subMap.key1.subStr", "test22");
        map.put("yzl.subMap.key1.subInteger", "123");
        map.put("yzl.subMap[com.yzl.test1.Test].subStr", "test13123");
        map.put("yzl.subMap[com.yzl.subStr.Test].subInteger", "121233");
        MapPropertySource mapPropertySource = new MapPropertySource("myMap", (Map) map);
        PropertySourcesBinder propertySourcesBinder = new PropertySourcesBinder(mapPropertySource);
        MyProperties config = new MyProperties();
        propertySourcesBinder.bindTo("yzl", config);

        System.out.println(config);
    }
}

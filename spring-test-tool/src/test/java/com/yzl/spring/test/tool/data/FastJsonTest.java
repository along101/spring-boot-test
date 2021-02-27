package com.yzl.spring.test.tool.data;

import com.yzl.spring.test.tool.data.bean.MyDemo1;
import com.yzl.spring.test.tool.data.bean.MyDemo2;
import com.yzl.spring.test.tool.data.bean.MyDemoBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yutu
 * @date 2021-02-02
 */
public class FastJsonTest {
    private ParserConfig config;

    @Before
    public void setUp() throws Exception {
        config = new ParserConfig();
        config.addAutoTypeCheckHandler((typeName, expectClass, features) -> {
            try {
                return Class.forName(typeName);
            } catch (Exception e) {
                throw new RuntimeException("Class.forName error.", e);
            }
        });
        config.putDeserializer(Object.class, new JavaBeanDeserializer(config, Object.class));
    }

    @Test
    public void test() {
        MyDemo1 demo1 = new MyDemo1();
        demo1.setA("a");
        demo1.setB(2);
        demo1.setId("1");

        MyDemo2 demo2 = new MyDemo2();
        demo2.setC("c");
        demo2.setD(3);
        demo2.setId("2");

        MyDemoBean t1 = new MyDemoBean();
        t1.setDemo1(demo1);
        t1.setDemo2(demo2);
        t1.setDemos(Arrays.asList(demo1, demo2));

        String text1 = JSON.toJSONString(t1, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(text1);

        ParserConfig config = new ParserConfig();
        config.addAutoTypeCheckHandler((typeName, expectClass, features) -> {
            try {
                return Class.forName(typeName);
            } catch (Exception e) {
                return null;
            }
        });
        MyDemoBean t2 = JSON.parseObject(text1, MyDemoBean.class, config, Feature.SupportAutoType);
        String text2 = JSON.toJSONString(t2, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(text2);

        Assert.assertEquals(text1, text2);
    }

    @Test
    public void testMapField() {
        MyDemo1 demo1 = new MyDemo1();
        demo1.setA("a");
        demo1.setB(2);
        demo1.setId("1");

        MyDemoBean t1 = new MyDemoBean();
        t1.setDemoMap(new HashMap<>());
        t1.getDemoMap().put("demo1", demo1);
        String text = JSON.toJSONString(t1, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(text);

        MyDemoBean t2 = JSON.parseObject(text, MyDemoBean.class, config, Feature.SupportAutoType);
        System.out.println(t2.getDemoMap().get("demo1").getClass());

        Assert.assertEquals(MyDemo1.class, t2.getDemoMap().get("demo1").getClass());
    }

    @Test
    public void testMap() {
        MyDemo1 demo1 = new MyDemo1();
        demo1.setA("a");
        demo1.setB(2);
        demo1.setId("1");
        Map<String, Object> map = new HashMap<>();
        map.put("demo1", demo1);

        String text = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(text);

        TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> map2 = JSON.parseObject(text, type.getType(), config, Feature.SupportAutoType);
        System.out.println(map2.get("demo1").getClass());
        Assert.assertEquals(MyDemo1.class, map2.get("demo1").getClass());
    }
}

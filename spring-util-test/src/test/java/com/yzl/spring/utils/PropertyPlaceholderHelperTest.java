package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

/**
 * 占位符处理,根据属性替换字符串中的占位符
 *
 * @author yinzuolong
 */
public class PropertyPlaceholderHelperTest {

    @Test
    public void test() {
        String a = "{name}-{age}-{sex}";
        String b = "{name1:yzl}-{age}-{sex}";

        Properties properties = new Properties();
        properties.setProperty("name", "along101");
        properties.setProperty("age", "18");
        properties.setProperty("sex", "男");

        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("{", "}", ":", false);

        System.out.println(helper.replacePlaceholders(a, properties));
        System.out.println(helper.replacePlaceholders(b, properties));
    }
}

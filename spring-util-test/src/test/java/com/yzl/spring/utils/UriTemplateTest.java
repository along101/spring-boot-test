package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.web.util.UriTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * uri模板工具，获取参数，替换参数
 *
 * @author yinzuolong
 */
public class UriTemplateTest {

    @Test
    public void test() {
        String uri = "/a/{userId}/{age}?grade={grade}";
        UriTemplate uriTemplate = new UriTemplate(uri);

        List<String> variableNames = uriTemplate.getVariableNames();
        System.out.println(variableNames);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", 123);
        params.put("age", 10);
        params.put("grade", "A+");
        System.out.println(uriTemplate.expand(params));

        System.out.println(uriTemplate.match("/a/12/4?grade=u"));
    }
}

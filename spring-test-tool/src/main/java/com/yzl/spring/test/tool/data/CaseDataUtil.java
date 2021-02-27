package com.yzl.spring.test.tool.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试案例数据工具类
 * 测试案例放在resources目录下，一般是xxx.json文件
 * 数据之间可以相互引用，使用$case设置引用的文件
 * 使用@type字段定义该json的实际类，主要是避免抽象类的
 * 举个例子：
 * {
 * "@type":"com.yzl.spring.test.tool.data.bean.MyDemoBean",
 * "id": 1,
 * "demo1":{
 * "$case":"demo/demo1.json"
 * }
 * }
 *
 * @author yzl
 * @date 2021-02-27
 */
public class CaseDataUtil {

    private static ParserConfig config = new ParserConfig();

    static {
        config.addAutoTypeCheckHandler((typeName, expectClass, features) -> {
            try {
                return Class.forName(typeName);
            } catch (Exception e) {
                throw new RuntimeException("Class.forName error.", e);
            }
        });
        config.putDeserializer(Object.class, new JavaBeanDeserializer(config, Object.class));
    }

    public static String getResourceString(String path) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8);
    }

    /**
     * 读取类路径下的case对象
     *
     * @param path
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T readCaseObject(String path, Class<T> tClass) {
        JSONObject json = readCaseJSON(path);
        return parseObject(json.toJSONString(), tClass);
    }

    /**
     * 读取类路径下的case列表
     *
     * @param path
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> readCaseList(String path, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        try {
            String text = getResourceString(path);
            JSONArray array = JSON.parseArray(text);
            for (Object o : array) {
                if (o instanceof JSONObject) {
                    JSONObject json = (JSONObject) o;
                    T t = parseObject(json.toJSONString(), tClass);
                    list.add(t);
                } else {
                    list.add((T) o);
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("readCaseObject list error " + path, e);
        }
    }

    /**
     * 读取类路径下的case为json对象，处理case引用
     *
     * @param path
     * @return
     */
    public static JSONObject readCaseJSON(String path) {
        try {
            String text = getResourceString(path);
            JSONObject json = JSON.parseObject(text, Feature.OrderedField);
            fillCase(json);
            return json;
        } catch (Exception e) {
            throw new RuntimeException("can't readCaseJSON " + path, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String json, Class<T> tClass) {
        return (T) JSON.parseObject(json, tClass, config,
                Feature.SupportAutoType,
                Feature.CustomMapDeserializer,
                Feature.OrderedField);
    }

    private static void fillCase(JSONObject json) {
        for (String key : json.keySet()) {
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                Object caseJson = getCaseObject((JSONObject) value);
                if (caseJson != null) {
                    json.put(key, caseJson);
                } else {
                    fillCase((JSONObject) value);
                }
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.size(); i++) {
                    Object item = array.get(i);
                    if (item instanceof JSONObject) {
                        Object caseJson = getCaseObject((JSONObject) item);
                        if (caseJson != null) {
                            array.set(i, caseJson);
                        } else {
                            fillCase((JSONObject) item);
                        }
                    }
                }
            }
        }
    }


    private static JSONObject getCaseObject(JSONObject json) {
        Object caseValue = json.get("$case");
        if (caseValue instanceof String) {
            return readCaseJSON((String) caseValue);
        }
        return null;
    }
}

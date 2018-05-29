package com.yzl.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author yinzuolong
 */
@Getter
@Setter
@ConfigurationProperties("yzl")
public class MyProperties {

    private String str;
    private Integer integer;
    private List<String> strList;
    private List<SubProperties> subProperties;

    private Map<String, String> subMapStr;
    private Map<String, SubProperties> subMap;

    @Getter
    @Setter
    public static class SubProperties {
        private String subStr;
        private Integer subInteger;
    }

}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

/**
 * Ant风格path匹配器
 *
 * @author yinzuolong
 */
public class AntPathMatcherTest {

    @Test
    public void match() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        System.out.println(antPathMatcher.match("com/**/A.java", "com/along101/test/A.java"));
    }
}

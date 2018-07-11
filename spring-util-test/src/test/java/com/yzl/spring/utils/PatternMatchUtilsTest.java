package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.PatternMatchUtils;

/**
 * 简单的Pattern匹配,用处不是很大
 *
 * @author yinzuolong
 */
public class PatternMatchUtilsTest {

    @Test
    public void test() {
        System.out.println(PatternMatchUtils.simpleMatch("abc**123", "abcx123"));
    }
}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;

/**
 * 很实用的数字工具
 *
 * @author yinzuolong
 */
public class NumberUtilsTest {

    @Test
    public void test() {
        BigDecimal b = NumberUtils.convertNumberToTargetClass(1L, BigDecimal.class);
        System.out.println(b);

        Long l = NumberUtils.parseNumber("0123", Long.class);
        System.out.println(l);
    }
}

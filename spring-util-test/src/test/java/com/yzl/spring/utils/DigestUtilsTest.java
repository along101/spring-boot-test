package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * md5工具类
 *
 * @author yinzuolong
 */
public class DigestUtilsTest {

    @Test
    public void test() {
        String s = "along101";
        String md5 = DigestUtils.md5DigestAsHex(s.getBytes());
        System.out.println(md5);
    }
}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.SystemPropertyUtils;

/**
 * 系统属性工具，解析${}参数
 *
 * @author yinzuolong
 */
public class SystemPropertyUtilsTest {

    @Test
    public void test() {
        System.setProperty("myName", "along");
        System.out.println(SystemPropertyUtils.resolvePlaceholders("myName=${myName}"));
        System.clearProperty("myName");
    }
}

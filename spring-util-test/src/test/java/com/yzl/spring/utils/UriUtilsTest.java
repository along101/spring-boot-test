package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.web.util.UriUtils;

/**
 * uri工具，encode和decode
 *
 * @author yinzuolong
 */
public class UriUtilsTest {

    @Test
    public void test() throws Exception {
        String query = "param1=along&param2=阿龙";
        String e = UriUtils.encodeQuery(query, "utf-8");
        System.out.println(e);

        String d = UriUtils.decode(e, "utf-8");
        System.out.println(d);
    }
}

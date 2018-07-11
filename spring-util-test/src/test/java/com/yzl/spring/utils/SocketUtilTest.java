package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.SocketUtils;

/**
 * @author yinzuolong
 */
public class SocketUtilTest {

    @Test
    public void test() {
        int port = SocketUtils.findAvailableTcpPort();
        System.out.println(port);
    }
}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.SocketUtils;

/**
 * 找到一个可用的端口，很实用的工具，原理是不断的用一个随机数字去开启一个ServerSocket，直到成功后关闭这个ServerSocket
 *
 * @author yinzuolong
 */
public class SocketUtilTest {

    @Test
    public void test() {
        int port = SocketUtils.findAvailableTcpPort();
        System.out.println(port);
    }
}

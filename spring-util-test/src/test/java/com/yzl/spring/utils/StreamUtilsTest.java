package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 流工具,很方便将进行流的拷贝
 *
 * @author yinzuolong
 */
public class StreamUtilsTest {

    @Test
    public void test() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("/readme.txt");
        try (InputStream in = resource.getInputStream()) {
            String txt = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            System.out.println(txt);
        }
    }
}

package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by yinzuolong on 2017/10/24.
 */
public class ResourceTest {
    @Test
    public void testClassPathResource() throws Exception {
        ClassPathResource resource = new ClassPathResource("/readme.txt");
        try (InputStream in = resource.getInputStream()) {
            String txt = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            System.out.println(txt);
        }
    }
}

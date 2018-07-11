package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 文件拷贝工具类
 *
 * @author yinzuolong
 */
public class FileCopyUtilsTest {

    @Test
    public void test() throws IOException {
        ClassPathResource resource = new ClassPathResource("/readme.txt");
        byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }
}

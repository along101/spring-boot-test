package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 很强的资源解析器，很方便的获取到classpath包括jar包中的资源文件
 *
 * @author yinzuolong
 */
public class PathMatchingResourcePatternResolverTest {

    @Test
    public void testGetResource() throws IOException {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = pathMatchingResourcePatternResolver.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "readme.txt");

        try (InputStream in = resource.getInputStream()) {
            String txt = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            System.out.println(txt);
        }
    }

    @Test
    public void testGetResources() throws IOException {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

        //pattern加ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX前缀,会从classpath中获取所有匹配上的resource
        //不同位置相同resource，jar包正斜杠反斜杠都有
        Resource[] resources = pathMatchingResourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "*/spring.factories");
        System.out.println(resources.length);


        //不同位置相同resource，jar包只有正斜杠，比较合乎要求,做包扫描的话,可以通过此方式获取所有资源,然后match匹配
        Resource[] resources1 = pathMatchingResourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "META-INF/spring.factories");
        System.out.println(resources1.length);

        //获取不到,pattern中有*/?,从默认的classpath中寻找匹配
        Resource[] resources2 = pathMatchingResourcePatternResolver.getResources("*/spring.factories");
        System.out.println(resources2.length);

        //只能获取一个,pattern中没有*/?,直接通过ResourceLoader获取,内部是通过ClassLoader获取的
        Resource[] resources3 = pathMatchingResourcePatternResolver.getResources("META-INF/spring.factories");
        System.out.println(resources3.length);
    }
}

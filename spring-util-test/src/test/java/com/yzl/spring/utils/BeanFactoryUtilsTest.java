package com.yzl.spring.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * bean工厂工具
 *
 * @author yinzuolong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeanFactoryUtilsTest.TestApplication.class)
public class BeanFactoryUtilsTest {

    @SpringBootApplication
    public static class TestApplication {
        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }
    }

    @Autowired
    private ListableBeanFactory context;

    @Test
    public void name() throws Exception {
        //使用BeanFactoryUtils获取context中所有的bean
        Map<String, TestApplication> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, TestApplication.class, true, false);
        System.out.println(matchingBeans);
    }
}

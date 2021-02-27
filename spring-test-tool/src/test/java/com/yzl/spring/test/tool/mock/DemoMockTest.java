package com.yzl.spring.test.tool.mock;

import com.yzl.spring.test.tool.mock.annotation.MyMockBean;
import com.yzl.spring.test.tool.mock.annotation.MyMockExclude;
import com.yzl.spring.test.tool.mock.annotation.MyMockRule;
import com.yzl.spring.test.tool.mock.demo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yutu
 * @date 2021-01-30
 */
@MyMockRule(expression = "beanName like 'demoBean%' ")
@MyMockExclude(beanName = "demoBean2")
@MyMockBean(name = "demoBean6", clazz = DemoBean6.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class DemoMockTest {

    @Autowired
    private MyMockPostProcessor myMockPostProcessor;

    @Autowired
    private DemoBean1 demoBean1;

    @Autowired
    private DemoBean2 demoBean2;

    @Autowired
    private DemoBean3 demoBean3;

    @Autowired
    private DemoBean4 demoBean4;

    @Autowired
    private DemoBean5 demoBean5;

    @Autowired
    private DemoBean6 demoBean6;

    @Autowired
    private DemoManager demoManager;

    @Before
    public void setUp() throws Exception {
        Mockito.doReturn("mock").when(demoBean1).sayHello(Mockito.anyString());
    }

    @Test
    public void testMock() {
        System.out.println(demoBean1.getClass().getSimpleName());
        Assert.assertTrue(demoBean1.getClass().getSimpleName().contains("Mock"));

        System.out.println(demoBean2.getClass().getSimpleName());
        Assert.assertFalse(demoBean2.getClass().getSimpleName().contains("Mock"));

        System.out.println(demoBean3.getClass().getSimpleName());
        Assert.assertTrue(demoBean3.getClass().getSimpleName().contains("Mock"));

        System.out.println(demoBean4.getClass().getSimpleName());
        Assert.assertTrue(demoBean4.getClass().getSimpleName().contains("Mock"));

        System.out.println(demoBean5.getClass().getSimpleName());
        Assert.assertTrue(demoBean5.getClass().getSimpleName().contains("Mock"));

        System.out.println(demoBean6.getClass().getSimpleName());
        Assert.assertTrue(demoBean6.getClass().getSimpleName().contains("Mock"));
    }

    @Test
    public void testManager() {
        String rs = demoManager.sayHello("yzl");
        System.out.println(rs);
        Assert.assertTrue(rs.contains("mock"));
    }
}

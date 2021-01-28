package com.yzl.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yutu
 * @date 2021-01-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockBeanTest {

    @MockBean
    private Bean1 bean1;

    @MockBean
    private Interface1 interface1;

    @Autowired
    private Bean2 bean2;

    @Test
    public void test() {
        System.out.println(bean1.getClass());

    }
}

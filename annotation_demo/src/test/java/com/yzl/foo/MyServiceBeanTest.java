package com.yzl.foo;

import com.yzl.foo.service.MyServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class MyServiceBeanTest {

    @Autowired
    private MyServiceBean myService;

    @Test
    public void testProxy() throws Exception {
        myService.sayHello("world");
    }
}

package com.yzl.foo;

import com.yzl.foo.client.MyClientBean;
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
public class MyServiceClientTest {

    @Autowired
    private MyClientBean myClientBean;

    @Test
    public void testProxy() throws Exception {
        myClientBean.testSay("world");
    }
}

package com.yzl.alexy.spring.client;

import com.yzl.alexy.spring.annotation.AlexyClient;
import com.yzl.alexy.spring.demo.Simple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientApplication.class)
public class ClientTest {

    @AlexyClient
    private Simple simple;

    @Test
    public void testSayHello() {
        System.out.println(simple);
        Assert.assertNotNull(simple);
    }
}

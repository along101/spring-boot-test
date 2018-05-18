package com.yzl.spring.feign;

import com.yzl.spring.feign.dto.User;
import com.yzl.spring.feign.remote.HelloClient;
import com.yzl.spring.feign.remote.HelloClient1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yinzuolong on 2017/10/24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FeignClientApplication.class)
public class HelloClient1Test {

    @Autowired
    private HelloClient1 helloClient;

    @Test
    public void testClient() throws Exception {
        String result = helloClient.hello("yzl");
        System.out.println(result);

        User user = helloClient.hello("yzl", 30);
        System.out.println(user);

        helloClient.hello(new User("yzl",12));
    }
}

package com.yzl.alexy.spring.server;

import com.yzl.alexy.spring.annotation.AlexyService;
import com.yzl.alexy.spring.demo.Simple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class ServerTest {

    @AlexyService
    private Simple simple;

    @Test
    public void testServer() throws Exception {
        simple.sayHello("alexy");
    }

}

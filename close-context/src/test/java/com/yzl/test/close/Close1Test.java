package com.yzl.test.close;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"test1=1"})
public class Close1Test {

    @Test
    public void test() {
        Assert.assertTrue(true);
    }

    @AfterClass
    public static void afterClass() throws InterruptedException {
        TestApplication.close();
    }
}

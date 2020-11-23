package com.yzl.spring.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;

/**
 * 使用以下命令可以跳过该测试，默认不跳过
 * mvn test -DskipDemoTests=true
 *
 * @author yutu
 * @date 2020-11-23
 */
@IfProfileValue(name = "skipDemoTests", values = {"", "false"})
@RunWith(MySpringTestRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoTest {

    @Test
    public void test() {
        String skipDemoTests = System.getProperty("skipDemoTests");
        System.out.println(skipDemoTests);
        Assert.fail("can't arrive here.");
    }
}

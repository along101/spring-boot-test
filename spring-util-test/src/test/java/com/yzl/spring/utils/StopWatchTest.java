package com.yzl.spring.utils;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * 代码执行时间的统计,简单的统计还可以,更加详细的统计可以使用dropwizard metrics
 *
 * @author yinzuolong
 */
public class StopWatchTest {

    @Test
    public void test() throws InterruptedException {
        StopWatch test1 = new StopWatch("test1");
        StopWatch test2 = new StopWatch("test2");
        test1.start();
        for (int i = 0; i < 10; i++) {
            test2.start("t1");
            TimeUnit.MILLISECONDS.sleep(100);
            test2.stop();
            test2.start("t2");
            TimeUnit.MILLISECONDS.sleep(10);
            test2.stop();
        }
        test1.stop();
        System.out.println(test1.prettyPrint());
        System.out.println(test2.prettyPrint());
    }
}

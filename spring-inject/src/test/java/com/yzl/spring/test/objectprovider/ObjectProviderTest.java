package com.yzl.spring.test.objectprovider;

import com.yzl.spring.test.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author yinzuolong
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ObjectProviderTest {

    @Autowired
    private ObjectProvider<List<AnInterface>> anInterfaces;

    @Test
    public void test() {
        List<AnInterface> interfaces = this.anInterfaces.getIfAvailable();
        interfaces.forEach(AnInterface::doSomething);
    }
}

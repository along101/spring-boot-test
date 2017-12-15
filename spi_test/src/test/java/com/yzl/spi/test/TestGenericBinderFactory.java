package com.yzl.spi.test;

import com.yzl.spi.GenericBinderFactory;
import org.junit.Assert;

import java.util.HashMap;

public class TestGenericBinderFactory {

    @org.junit.Test
    public void name() {

        GenericBinderFactory<TestInterface> factory = new GenericBinderFactoryTest();
        TestInterface test2 = factory.generate("test2");
        Assert.assertEquals(TestImpl2.class, test2.getClass());
    }
}
class A extends HashMap<String,Integer> {}
class GenericBinderFactoryTest extends GenericBinderFactory<TestInterface> {

}
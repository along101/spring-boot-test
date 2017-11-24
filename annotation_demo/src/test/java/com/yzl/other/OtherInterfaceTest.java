package com.yzl.other;

import com.yzl.other.api.OtherInterface;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yinzuolong on 2017/11/24.
 */
public class OtherInterfaceTest {

    @Autowired
    private OtherInterface otherInterface;

    @Test
    public void testOther() throws Exception {
        otherInterface.test();
    }
}

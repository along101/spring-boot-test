package com.yzl.other.api;

import com.yzl.spring.anno.MyClient;

/**
 * Created by yinzuolong on 2017/11/24.
 */

@MyClient("OtherClient")
public interface OtherInterface {

    String test();
}

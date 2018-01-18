package com.yzl.spring.actuator.customize;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

/**
 * Created by yinzuolong on 2017/11/24.
 */
@Component
public class MyEndPoint extends AbstractEndpoint {

    public MyEndPoint() {
        super("my", false);
    }

    @Override
    public Object invoke() {
        return "hello world!";
    }
}

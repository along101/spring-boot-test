package com.yzl.spring.test;

import org.springframework.stereotype.Component;

/**
 * @author yutu
 * @date 2021-01-28
 */
@Component
public class Bean1 {
    private String hello() {
        return "hello";
    }
}

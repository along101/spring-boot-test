package com.yzl.spring.test;

import org.springframework.stereotype.Component;

/**
 * @author yutu
 * @date 2021-01-28
 */
@Component
public class Bean2 {

    private Bean1 bean1;

    private Interface1 interface1;

    public Bean2(Bean1 bean1, Interface1 interface1) {
        this.bean1 = bean1;
        this.interface1 = interface1;
    }

    public void setBean1(Bean1 bean1) {
        this.bean1 = bean1;
    }

    public void setInterface1(Interface1 interface1) {
        this.interface1 = interface1;
    }
}

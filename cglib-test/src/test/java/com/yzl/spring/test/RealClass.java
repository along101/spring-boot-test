package com.yzl.spring.test;

/**
 * @author yutu
 * @date 2021-01-28
 */
public class RealClass {

    public void hello() {
        System.out.println("我是真实类要处理的东东");
        inner();
    }

    private void inner() {
        System.out.println("我是真实内部私有方法");
    }
}

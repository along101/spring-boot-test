package com.yzl.test;

import java.util.Arrays;

/**
 * @author yutu
 * @date 2018/10/29
 */
public class Test {

    public static void main(String[] args) {
        while (true) {
            Arrays.asList(1, 2, 3).forEach(i -> {
                System.out.println("test:");
                print(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void print(int i) {
        System.out.println(i);
    }
}

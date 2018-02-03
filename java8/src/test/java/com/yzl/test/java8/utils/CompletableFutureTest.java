package com.yzl.test.java8.utils;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {

    private static void sleep(int s) {
        try {
            TimeUnit.SECONDS.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            sleep(1);
            //告诉completableFuture任务已经完成
            completableFuture.complete("result");
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    @Test
    public void test2() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            try {
                //模拟执行耗时任务
                System.out.println("task doing...");
                sleep(1);
                throw new RuntimeException("抛异常了");
            } catch (Exception e) {
                //告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    @Test
    public void test3() throws Exception {
        //supplyAsync内部使用ForkJoinPool线程池执行任务
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            sleep(1);
            //返回结果
            return "result";
        });
        System.out.println("计算结果:" + completableFuture.get());
    }


    @Test
    public void test4() throws Exception {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            sleep(1);
            //返回结果
            return "result1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            sleep(1);
            //返回结果
            return "result2";
        });

        CompletableFuture<Object> anyResult = CompletableFuture.anyOf(completableFuture1, completableFuture2);
        System.out.println("第一个完成的任务结果:" + anyResult.get());
        CompletableFuture<Void> allResult = CompletableFuture.allOf(completableFuture1, completableFuture2);
        //阻塞等待所有任务执行完成
        allResult.join();
        System.out.println("所有任务执行完成");

    }

    @Test
    public void test5() throws Exception {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            sleep(1);
            //返回结果
            return "result1";
        });

        //等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
        CompletableFuture<String> completableFuture2 = completableFuture1.thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            sleep(1);
            //返回结果
            return "result2";
        }));

        System.out.println(completableFuture2.get());

    }

    @Test
    public void test6() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            sleep(1);
            //返回结果
            return 100;
        });

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture2 = completableFuture1.thenCombine(
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    System.out.println("task2 doing...");
                    sleep(1);
                    //返回结果
                    return 2000;
                }),
                //合并函数
                (result1, result2) -> result1 + result2);

        System.out.println(completableFuture2.get());
    }

    @Test
    public void test7() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            sleep(1);
            //返回结果
            return 100;
        });

        //注册完成事件
        completableFuture1.thenAccept(result -> System.out.println("task1 done,result:" + result));
        //第二个任务
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            sleep(1);
            //返回结果
            return 2000;
        });

        //注册完成事件
        completableFuture2.thenAccept(result -> System.out.println("task2 done,result:" + result));

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture3 = completableFuture1.thenCombine(completableFuture2,
                //合并函数
                (result1, result2) -> {
                    sleep(1);
                    return result1 + result2;
                });

        System.out.println(completableFuture3.get());
    }

    @Test
    public void test8() {
        CompletableFuture.supplyAsync(() -> {
            sleep(2);
            System.out.println("complete");
            return 2;
        });
        sleep(3);
    }
}
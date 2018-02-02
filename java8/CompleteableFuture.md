# java8 reactor模式的实现之CompletableFuture 
java8中新增加了java.util.concurrent.CompletableFuture类，作用类似RxJava。

实际开发中，我们经常需要达成以下目的：

- 将两个异步计算合并为一个——这两个异步计算之间相互独立，同时第二个又依赖于第一个的结果。
- 等待 Future 集合中的所有任务都完成。
- 仅等待 Future 集合中最快结束的任务完成（有可能因为它们试图通过不同的方式计算同一个值），并返回它的结果。
- 通过编程方式完成一个 Future 任务的执行（即以手工设定异步操作结果的方式）。
- 应对 Future 的完成事件（即当 Future 的完成事件发生时会收到通知，并能使用 Future 计算的结果进行下一步的操作，不只是简单地阻塞等待操作的结果）

新的CompletableFuture将使得这些成为可能。

## 工厂构建
- runAsync , supplyAsync
> 静态方法，创建一个CompletableFuture，runAsync没有返回值，supplyAsync有返回值

- completedFuture
> 静态方法，创建一个已经完成的CompletableFuture

## 等待完成
- get
> 等待完成，并获取结果

- join
> 将异步执行的线程join过来，跟get类似，等待完成，但是是不返回结果

## 设置状态
- complete,completeExceptionally
> 设置CompletableFuture为完成，用指定的对象返回

- obtrudeValue,obtrudeException
> 强制返回/抛异常

- cancel
> 取消

## 根据状态操作
- whenComplete
> 当本 CompletableFuture 执行完成后使用 BiConsumer 函数返回一个新的CompletableFuture， BiConsumer 第一个参数为返回值，第二个参数为异常

- handle
> 当本CompletableFuture 执行完成后执行 BiConsumer 的函数，BiFunction第一个参数为返回值，第二个参数为异常，需要返回

- exceptionally
> 当本CompletableFuture执行异常时，使用Function返回一个新的CompletableFuture

## 状态判断
- isDone,isCompletedExceptionally,isCancelled
> 返回是否完成/异常/取消

## 逻辑转换，单个
- thenApply,thenApplyAsync
> CompletableFuture正常结束后，使用Function函数应用一个新的CompletableFuture，Function函数有返回值

- thenAccept
> CompletableFuture正常结束后，使用Consumer函数应用一个新的CompletableFuture，Consumer函数没有返回值

- thenRun
> CompletableFuture正常结束后，使用Runnable函数应用一个新的CompletableFuture，Runnable函数没有输入值和返回值

# 逻辑转换，两个
- thenAcceptBoth
> 当本CompletableFuture和给定的CompletableFuture正常完成后，执行BiFunction函数并返回，BiFunction的输入参数为两个CompletableFuture的返回值

- thenCombine
> 当本CompletableFuture和给定的CompletableFuture正常完成后，使用BiFunction函数生成一个新的CompletableFuture，BiFunction的输入参数为两个CompletableFuture的返回值。
> 两个CompletableFuture执行完后再执行指定函数

- thenCompose
> 当本CompletableFuture完成后，将返回结果作为参数传递给Function函数，此Function函数返回一个新的CompletableFuture。
> 第一个CompletableFuture执行完后，结果传递给第二个CompletableFuture，返回第二个CompletableFuture

- applyToEither
> 本 CompletableFuture 或者给定的CompletableFuture，任意一个做完后，使用Function生成一个新的CompletableFuture并返回

- acceptEither
> 本 CompletableFuture 或者给定的CompletableFuture，任意一个做完后，使用Consumer生成一个新的CompletableFuture并返回

- runAfterEither
> 本 CompletableFuture 或者给定的 CompletableFuture，任意一个做完后，使用Runnable生成一个新的CompletableFuture并返回

- runAfterBoth
> 本CompletableFuture和给定的CompletableFuture，都完成后，使用Runnable生成一个新的CompletableFuture并返回

## 逻辑转换，多个
- allOf
> 静态方法，返回一个新的CompletableFuture，当所有的CompletableFuture都完成后才完成

- anyOf
> 静态方法，返回一个新的CompletableFuture，任意一个CompletableFuture都完成后就完成


# demo


```

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

}
```

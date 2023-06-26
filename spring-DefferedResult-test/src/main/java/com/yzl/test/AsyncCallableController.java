package com.yzl.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

@RestController
public class AsyncCallableController {

    /**
     * 异步处理，采用spring里面的线程池
     *
     * @return
     */
    @RequestMapping("callable")
    public Callable<String> executeSlowTask() {
        System.out.println("start...");
        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "123";
        };
        System.out.println("end!");
        return callable;
    }

    /**
     * 异步设置超时时间
     *
     * @return
     */
    @RequestMapping("timeout")
    public WebAsyncTask<String> executeTimeTask(@RequestParam("time") int time) {

        System.out.println("start...");
        Callable<String> callable = () -> {
            Thread.sleep(time);
            return "123";
        };
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(2000, callable);
        asyncTask.onTimeout(() -> "time out");
        asyncTask.onCompletion(() -> System.out.println("complete."));
        System.out.println("end!");
        return asyncTask;
    }
}

package com.yzl.test.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yutu
 * @date 2018/9/27
 */
@RestController
public class MyController {

    @Autowired
    private MyThreadPoolTask myThreadPoolTask;

    @GetMapping(path = "/schedule/cancel")
    public String cancel() {
        myThreadPoolTask.cancel();
        return "success";
    }

    @GetMapping(path = "/schedule/start")
    public String start() {
        myThreadPoolTask.start();
        return "success";
    }
}

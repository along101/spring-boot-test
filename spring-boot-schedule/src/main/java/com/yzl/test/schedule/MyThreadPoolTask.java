package com.yzl.test.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * @author yutu
 * @date 2018/9/27
 */
@Component
public class MyThreadPoolTask implements Runnable {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    public void start() {
        if (future == null || future.isCancelled()) {
            future = threadPoolTaskScheduler.schedule(this, new CronTrigger("0/5 * * * * *"));
        }
    }

    @Override
    public void run() {
        System.out.println("MyThreadPoolTask : The time is now " + dateFormat.format(new Date()));
    }

    public void cancel() {
        if (future != null) {
            future.cancel(true);
        }
    }
}

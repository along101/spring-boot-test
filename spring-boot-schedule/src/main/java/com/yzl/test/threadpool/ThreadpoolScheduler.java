package com.yzl.test.threadpool;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yutu
 * @date 2018/9/27
 */
@Component
public class ThreadpoolScheduler {
    private ScheduledThreadPoolExecutor connectionManagerSchedule;
    private Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        CustomizableThreadFactory customizableThreadFactory = new CustomizableThreadFactory("mySchedule");
        customizableThreadFactory.setDaemon(true);
        connectionManagerSchedule = new ScheduledThreadPoolExecutor(5, customizableThreadFactory);

    }

    synchronized public void schedule(String name, Runnable runnable, long initialDelay, long period) {
        if (futureMap.get(name) != null) {
            throw new RuntimeException("name exist: " + name);
        }
        ScheduledFuture<?> future = connectionManagerSchedule.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
        futureMap.put(name, future);
    }

    synchronized public void cancel(String name, boolean mayInterruptIfRunning) {
        if (futureMap.get(name) == null) {
            return;
        }
        futureMap.get(name).cancel(mayInterruptIfRunning);
        futureMap.remove(name);
    }

    synchronized public void shutdown(long wait) throws InterruptedException {
        try {
            connectionManagerSchedule.shutdown();
            connectionManagerSchedule.awaitTermination(wait, TimeUnit.MILLISECONDS);
        } finally {
            futureMap.clear();
        }
    }

    synchronized public void shutdownNow() {
        connectionManagerSchedule.shutdownNow();
        futureMap.clear();
    }
}

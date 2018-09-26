package com.yzl.test.schedule;

/**
 * @author yutu
 * @date 2018/9/26
 */

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        System.out.println("Scheduling Tasks Examples By Cron: The time is now " + dateFormat.format(new Date()));
    }
}

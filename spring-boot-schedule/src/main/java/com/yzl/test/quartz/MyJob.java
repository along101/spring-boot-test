package com.yzl.test.quartz;

import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yutu
 * @date 2018/9/26
 */
@Component
public class MyJob {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void execute() throws JobExecutionException {
        System.out.println("MyJob Examples: The time is now " + dateFormat.format(new Date()));
    }
}

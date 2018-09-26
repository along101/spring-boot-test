package com.yzl.test.quartz;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author yutu
 * @date 2018/9/26
 */
@RestController
public class UpdateController {

    @Autowired
    private ObjectProvider<List<CronTrigger>> cronTriggers;

    @Resource(name = "myScheduler")
    private Scheduler scheduler;

    @GetMapping(path = "/updateCron")
    public void updateCron(@RequestParam("jobName") String jobName, @RequestParam("cron") String cron) {
        Optional<CronTrigger> optional = cronTriggers.getIfAvailable().stream()
                .filter(cronTrigger -> cronTrigger.getJobKey().toString().equals(jobName)).findFirst();
        optional.ifPresent(cronTrigger -> {
            CronTriggerImpl newCronTrigger = new CronTriggerImpl();
            try {
                newCronTrigger.setCronExpression(cron);
                newCronTrigger.setJobKey(cronTrigger.getJobKey());
                newCronTrigger.setName(cronTrigger.getKey().getName());
                scheduler.rescheduleJob(cronTrigger.getKey(), newCronTrigger);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

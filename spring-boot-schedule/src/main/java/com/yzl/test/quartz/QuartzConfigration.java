package com.yzl.test.quartz;

import org.quartz.Trigger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * @author yutu
 * @date 2018/9/26
 */
@Configuration
public class QuartzConfigration {

    @Bean(name = "myJobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(MyJob myJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        // 是否并发执行
        jobDetail.setConcurrent(false);

        jobDetail.setName("MyJob");
        jobDetail.setGroup("yzl");

        jobDetail.setTargetObject(myJob);

        //sayHello为需要执行的方法
        jobDetail.setTargetMethod("execute");
        return jobDetail;
    }

    @Bean(name = "myJobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail.getObject());
        tigger.setCronExpression("0/5 * * * * ?");
        tigger.setName("yzl");
        return tigger;
    }

    /**
     * attention:
     * Details：定义quartz调度工厂
     */
    @Bean(name = "myScheduler")
    public SchedulerFactoryBean schedulerFactory(ObjectProvider<List<Trigger>> cronJobTriggers) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        List<Trigger> triggerList = cronJobTriggers.getIfAvailable();
        bean.setTriggers(triggerList.toArray(new Trigger[0]));
        return bean;
    }

}

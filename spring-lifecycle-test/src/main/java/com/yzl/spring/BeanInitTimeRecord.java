package com.yzl.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yutu
 * @date 2021-01-25
 */
@Component
public class BeanInitTimeRecord implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    public Map<String, Record> records = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String key = beanName;
        Record record = records.getOrDefault(key, new Record());
        record.key = (key);
        record.start = (System.currentTimeMillis());
        records.put(key, record);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String key = beanName;
        Record record = records.getOrDefault(key, new Record());
        record.end = (System.currentTimeMillis());
        record.cost = (record.end - record.start);
        System.out.println(key + " cost:" + record.cost);
        return bean;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ArrayList<Record> list = new ArrayList<>(records.values());
        list.sort((r1, r2) -> (int) (r1.cost - r2.cost));
        String s = list.stream().map(r -> r.key + " cost: " + r.cost).collect(Collectors.joining("\n"));
        System.out.println(s);
    }

    public static class Record {
        private String key;
        private long start;
        private long end;
        private long cost;
    }
}

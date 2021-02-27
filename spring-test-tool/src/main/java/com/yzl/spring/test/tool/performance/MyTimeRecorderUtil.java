package com.yzl.spring.test.tool.performance;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yutu
 * @date 2021-01-25
 */
@Slf4j
public class MyTimeRecorderUtil {

    public static final Map<String, Record> records = new ConcurrentHashMap<>();

    public static synchronized Record recordStart(String key) {
        Record record = records.getOrDefault(key, new Record(key));
        record.start = System.currentTimeMillis();
        records.put(key, record);
        log.debug("{} recordStart", key);
        return record;
    }

    public static synchronized Record recordEnd(String key) {
        Record record = records.getOrDefault(key, new Record(key));
        record.end = System.currentTimeMillis();
        records.put(key, record);
        log.debug("{} recordEnd cost: {}", key, record.getCost());
        return record;
    }


    @Getter
    public static class Record {
        Record(String key) {
            this.key = key;
            this.start = System.currentTimeMillis();
        }
        private String key;
        private long start;
        private long end;

        long getCost() {
            return end - start;
        }
    }
}

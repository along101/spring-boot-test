package com.yzl.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class LangPollController {

    private Map<String, Set<DeferredResult<String>>> connectMap = new ConcurrentHashMap<>();

    /**
     * 长拉请求，该请求会hold住10秒钟，10秒内如果有个请求通知会立即返回
     *
     * @param testId
     * @return
     */
    @RequestMapping("poll")
    public DeferredResult<String> poll(@RequestParam("testId") String testId) {
        DeferredResult<String> deferredResult = new DeferredResult<>(10 * 1000L, testId + " time out!");
        Set<DeferredResult<String>> set = connectMap.putIfAbsent(testId, Collections.synchronizedSet(new HashSet<>()));
        if (set == null) {
            set = connectMap.get(testId);
        }
        Set<DeferredResult<String>> finalSet = set;
        set.add(deferredResult);

        deferredResult.onCompletion(() -> finalSet.remove(deferredResult));
        deferredResult.onTimeout(() -> System.out.println("poll " + testId + " time out!"));
        return deferredResult;
    }

    /**
     * 发送一个请求，通知对应的id准备好，poll请求会立即返回
     *
     * @param testId
     * @param value
     * @return
     */
    @RequestMapping("notify")
    public String notifyTestId(@RequestParam("testId") String testId, @RequestParam("value") String value) {
        Set<DeferredResult<String>> results = connectMap.getOrDefault(testId, new HashSet<>());
        results.forEach(result -> {
            if (result.isSetOrExpired()) {
                return;
            }
            result.setResult("testId " + testId + " value is " + value);
        });
        return "notified " + results.size() + " of testId " + testId;
    }
}

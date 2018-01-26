#spring 实现lang polling 
很简单的实现，通过controller返回DeferredResult实现。

## 快速入门
```java
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
```
- /poll 请求创建一个DeferredResult对象，将这个对象放在hashmap中，如果超时或者请求完成就删除这个对象。
- /notify 请求通知对应的DeferredResult完成操作
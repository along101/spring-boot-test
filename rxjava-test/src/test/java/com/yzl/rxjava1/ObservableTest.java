package com.yzl.rxjava1;

import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

//TODO 测试其他场景
public class ObservableTest {
    @Test
    public void name() {
        List<Integer> ls = Arrays.asList(1, 2, 34, 44);
        Observable.from(ls).subscribe(System.out::println);
    }
}

package com.yzl.rxjava1;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//TODO 测试其他场景
public class ObservableTest {
    @Test
    public void test1() {
        List<Integer> ls = Arrays.asList(1, 2, 34, 44);
        Observable.from(ls).subscribe(System.out::println);
    }

    @Test
    public void test2() throws InterruptedException {
        //subscribeOn 异步操作
        Observable<Long> observable = Observable.just(1, 2)
                .subscribeOn(Schedulers.io()).map(new Func1<Integer, Long>() {
                    @Override
                    public Long call(Integer t) {
                        try {
                            Thread.sleep(1000); //耗时的操作
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return (long) (t * 2);
                    }
                });
        observable.subscribe(new TestObserver<>());
        System.out.println("other...");
        Thread.sleep(3000);
    }

    @Test
    public void test3() {
        Observable<Integer> observable = Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> observer) {
                        for (int i = 0; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                });
        observable.subscribe(new TestObserver<>());
    }

    public static class TestObserver<T> implements Observer<T> {

        @Override
        public void onCompleted() {
            System.out.println("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("Throwable: " + e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(T t) {
            System.out.println("onNext: " + t);
        }
    }
}

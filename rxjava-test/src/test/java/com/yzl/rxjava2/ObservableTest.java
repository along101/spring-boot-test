package com.yzl.rxjava2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Observable/Observer 观察者模式
 *
 * http://blog.csdn.net/jdsjlzx/article/details/54845517
 */
public class ObservableTest {

    @Test
    public void base() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("int : " + integer);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("complete.");
            }
        });
    }

    @Test
    public void list() {
        List<Integer> ls = Arrays.asList(1, 2, 34, 44);
        Observable.fromIterable(ls).subscribe(System.out::println).dispose();
    }
}

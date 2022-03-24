package com.ccand99.rxjava1;

import org.junit.Test;
import rx.Observable;
import rx.exceptions.CompositeException;
import rx.observers.TestSubscriber;

public class ConcatMapDelayErrorDemo {
    @Test
    public void concatMapDelayError(){
        Observable<Integer> obs = Observable
                .just(3,0,2,0,1,0)
                .concatMapDelayError(x -> Observable.fromCallable(() -> 100/x)); // x/0会保错
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        obs.subscribe(ts);

        ts.assertValues(33,50,100);
        //ts.assertError(ArithmeticException.class); // 失败
        //3个异常合并成了一个异常
        ts.assertError(CompositeException.class);
    }
}

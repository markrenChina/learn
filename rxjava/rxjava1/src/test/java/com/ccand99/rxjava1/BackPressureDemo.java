package com.ccand99.rxjava1;

import org.junit.Test;
import rx.Observable;
import rx.observables.SyncOnSubscribe;
import rx.observers.TestSubscriber;

public class BackPressureDemo {

    //不支持背压
    Observable<Long> naturals1(){
        return Observable.create( subscriber -> {
            long i = 0;
            while (!subscriber.isUnsubscribed()){
                subscriber.onNext(i++);
            }
        });
    }

    //支持背压
    Observable<Long> naturals2(){
        return Observable.create(
                SyncOnSubscribe.createStateful(
                        () -> 0L,
                        (cur , observer) -> {
                            observer.onNext(cur);
                            return cur +1;
                        }));
    }

    @Test
    public void isSupportBackPressure1(){
        //0 == request（0） 1 不支持背压assertNoValues会失败
        TestSubscriber<Long> ts = new TestSubscriber<>(0);

        naturals1()
                .take(10)
                .subscribe(ts);

        //No onNext events expected yet some received: 10 (1 completion)
        ts.assertNoValues();
        ts.requestMore(100);
        ts.assertValueCount(10);
        ts.assertCompleted();
    }

    @Test
    public void isSupportBackPressure2(){
        TestSubscriber<Long> ts = new TestSubscriber<>(0);

        naturals2()
                .take(10)
                .subscribe(ts);

        ts.assertNoValues();
        ts.requestMore(100);
        ts.assertValueCount(10);
        ts.assertCompleted();
    }
}

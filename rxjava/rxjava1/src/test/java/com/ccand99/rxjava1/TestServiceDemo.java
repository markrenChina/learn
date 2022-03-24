package com.ccand99.rxjava1;

import com.ccand99.rxjava1.service.MyService;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TestServiceDemo {

    private MyServiceWithTimeout mockReturning(
            Observable<LocalDate> result,
            TestScheduler testScheduler
    ){
        MyService mock = mock(MyService.class);
        given(mock.externalCall()).willReturn(result);
        return new MyServiceWithTimeout(mock,testScheduler);
    }

    static class MyServiceWithTimeout implements MyService{

        //持有一个要测试目标
        private final MyService delegate;
        private final Scheduler scheduler;

        public MyServiceWithTimeout(MyService delegate, Scheduler scheduler) {
            this.delegate = delegate;
            this.scheduler = scheduler;
        }

        @Override
        public Observable<LocalDate> externalCall() {
            return delegate
                    .externalCall()
                    .timeout(1, TimeUnit.SECONDS,Observable.empty(),scheduler);
        }
    }

    //1.如果externalCall永远不会结束
    @Test
    public void timeoutWhenServiceNeverCompletes() throws Exception {
        //given
        TestScheduler testScheduler = Schedulers.test();
        MyService mock = mockReturning(
                Observable.never(),//never 永远不会完成，并且发布任何值
                testScheduler);
        TestSubscriber<LocalDate> ts = new TestSubscriber<>();

        //when
        mock.externalCall().subscribe(ts);

        //then 手动推进时间
        testScheduler.advanceTimeBy(950,TimeUnit.MILLISECONDS); //timeout没有到
        ts.assertNoTerminalEvent();
        testScheduler.advanceTimeBy(100,TimeUnit.MILLISECONDS); //超过timeout时间
        ts.assertCompleted();
        ts.assertNoValues();
    }

    //2.确保正常情况下超时不会发挥作用
    @Test
    public void valueIsReturnedJustBeforeTimeOut() throws Exception {
        //given
        TestScheduler testScheduler = Schedulers.test();
        Observable<LocalDate> slow = Observable
                .timer(950, TimeUnit.MILLISECONDS, testScheduler)
                .map( x -> LocalDate.now());
        MyService myService = mockReturning(slow, testScheduler);
        TestSubscriber<LocalDate> ts = new TestSubscriber<>();

        //when
        myService.externalCall().subscribe(ts);

        //then
        testScheduler.advanceTimeBy(930,TimeUnit.MILLISECONDS);
        ts.assertNotCompleted();
        ts.assertNoValues();
        testScheduler.advanceTimeBy(50,TimeUnit.MILLISECONDS);
        ts.assertCompleted();
        ts.assertValueCount(1);
    }
}

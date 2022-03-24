package com.ccand99.rxjava1;

import org.junit.Before;
import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.TestScheduler;

public class HookSchedulersDemo {
    private final TestScheduler testScheduler = new TestScheduler();

    @Before
    public void alwaysUseTestScheduler(){
        RxJavaPlugins
                .getInstance()
                .registerSchedulersHook( new RxJavaSchedulersHook(){
                    @Override
                    public Scheduler getComputationScheduler() {
                        return testScheduler;
                    }

                    @Override
                    public Scheduler getIOScheduler() {
                        return testScheduler;
                    }

                    @Override
                    public Scheduler getNewThreadScheduler() {
                        return testScheduler;
                    }
                });
    }
}

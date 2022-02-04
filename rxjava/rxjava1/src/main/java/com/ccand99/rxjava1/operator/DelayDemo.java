package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * delay 操作符示例
 */
public class DelayDemo {

    public static void main(String[] args) {
        //demo1();
        //demo2();
        demo3();
    }

    public static void demo1(){
        //1，2，3会在RxComputationScheduler-1 同时到达
        Observable.just(1,2,3).delay(1, SECONDS).subscribe(Utils::log);
        sleep(4,SECONDS);
    }

    public static void demo2(){
        Observable
                .timer(1, SECONDS)
                .flatMap(i -> Observable.just(1,2,3))
                .subscribe(Utils::log);
        sleep(4,SECONDS);
    }

    //限制flatmap的并发数为1，delay不会同时道达
    public static void demo3(){
        Observable.just(1,2,3)
                .delay(1, SECONDS)
                .flatMap(Observable::just,1)
                //.concatMap(Observable::just)
                .subscribe(Utils::log);
        sleep(4,SECONDS);
    }
}

package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.time.DayOfWeek;

import static com.ccand99.rxjava1.base.Utils.sleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class FlatMapDemo {

    public static void main(String[] args) {
        //demo1();
        demo2();
    }

    public static void demo1(){
        Observable.just(1)
                .flatMap(i -> Observable.just(2 * i,3 * i,4 * i))
                .subscribe(System.out::println);
    }

    public static void demo2(){
        Observable.just(DayOfWeek.SUNDAY,DayOfWeek.MONDAY)
                .flatMap(FlatMapDemo::loadRecordsFor)
                .subscribe(Utils::log);
        sleep(5,SECONDS);
    }

    public static Observable<String> loadRecordsFor(DayOfWeek dow){
        switch (dow){
            case SUNDAY:
                return Observable
                        .interval(90,MILLISECONDS)
                        .take(5)
                        .map(i -> "Sun-" + i );
            case MONDAY:
                return Observable
                        .interval(65,MILLISECONDS)
                        .take(5)
                        .map(i -> "Mon-" + i );
            default: return Observable.empty();
        }
    }
}

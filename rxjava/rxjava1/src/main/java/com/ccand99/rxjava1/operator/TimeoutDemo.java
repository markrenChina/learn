package com.ccand99.rxjava1.operator;

import rx.Observable;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;
import static java.time.Month.*;


public class TimeoutDemo {

    static Observable<LocalDate> nextSolarEclipse(LocalDate after){
        return Observable.just(
                LocalDate.of(2016, MARCH , 9),
                LocalDate.of(2016,SEPTEMBER,1),
                LocalDate.of(2017,FEBRUARY,26),
                LocalDate.of(2017,AUGUST,21),
                LocalDate.of(2018,FEBRUARY,15),
                LocalDate.of(2018,JULY,13),
                LocalDate.of(2018,AUGUST,11),
                LocalDate.of(2019,JANUARY,6),
                LocalDate.of(2019,JULY,2),
                LocalDate.of(2019,DECEMBER,26)
        ).skipWhile(date -> !date.isAfter(after))
                .zipWith(
                        Observable.interval(500,50, TimeUnit.MILLISECONDS),
                        (date, x) ->date
                );
    }

    public static void main(String[] args) {
        nextSolarEclipse(LocalDate.of(2016,SEPTEMBER,1))
                .timeout(
                        ()-> Observable.timer(1000,TimeUnit.MILLISECONDS),
                        date -> Observable.timer(100,TimeUnit.MILLISECONDS)
                )
                //.timeInterval()
                .subscribe(System.out::println);
        sleep(10,TimeUnit.SECONDS);
    }
}

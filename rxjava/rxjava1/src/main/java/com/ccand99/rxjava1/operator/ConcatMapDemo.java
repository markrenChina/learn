package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

import java.time.DayOfWeek;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * concatMap 示例
 */
public class ConcatMapDemo {

    public static void main(String[] args) {
        Observable.just(DayOfWeek.SUNDAY,DayOfWeek.MONDAY)
                .concatMap(FlatMapDemo::loadRecordsFor)
                .subscribe(Utils::log);
        sleep(5, TimeUnit.SECONDS);
    }
}

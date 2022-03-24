package com.ccand99.rxjava1.operator;

import rx.Observable;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

public class DebounceDemo {

    static Observable<BigDecimal> pricesOf(String ticker){
        return Observable
                .interval(50, TimeUnit.MILLISECONDS)
                .flatMap(DebounceDemo::randomDelay)
                .map(DebounceDemo::randomStockPrice)
                .map(BigDecimal::valueOf);
    }

    static Observable<Long> randomDelay(long x){
        return Observable
                .just(x)
                .delay((long) (Math.random() * 100),TimeUnit.MILLISECONDS);
    }

    //long 类型转化为随机抖动的正弦曲线
    static double randomStockPrice(long x){
        return 100 + Math.random() * 10 + (Math.sin( x / 100.0)) * 60.0;
    }

    //超过150，10毫秒发布，不然100毫秒
    public static void main(String[] args) {
        pricesOf("")
                .debounce(x -> {
                    boolean goodPrice = x.compareTo(BigDecimal.valueOf(150)) > 0;
                    return Observable.empty()
                            .delay(goodPrice ? 10 : 100, TimeUnit.MILLISECONDS);
                })
                .subscribe(System.out::println);

        sleep(10,TimeUnit.SECONDS);
    }
}

package com.ccand99.rxjava1.scheduler.sample;

import com.ccand99.rxjava1.base.MySchedulers;
import org.apache.commons.lang3.tuple.Pair;
import rx.Observable;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.*;

/**
 * 假设有一个杂货店
 */
public class RxGroceries {

    static Observable<BigDecimal> purchase(String productName, int quantity){
        return Observable.fromCallable(() -> doPurchase(productName, quantity));
    }

    private static BigDecimal doPurchase(String productName, int quantity) {
        log("Purchasing " + quantity + " " + productName);
        sleep(1, TimeUnit.SECONDS);
        int price = random(10) * quantity;
        log("Done "+ quantity +" " + productName);
        return new BigDecimal(price);
    }

    //1.synchronization
    //because it only one worker flow
    public static Observable<BigDecimal> sync(){
        return Observable
                .just("bread","butter","milk","tomato","cheese")
                .subscribeOn(MySchedulers.schedulerA)
                .map(prod -> doPurchase(prod ,1))
                .reduce(BigDecimal::add)
                .single();
    }

    //2.async
    public static Observable<BigDecimal> async(){
        return Observable
                .just("bread","butter","milk","tomato","cheese")
                .flatMap(prod -> purchase(prod ,1)
                        .subscribeOn(MySchedulers.schedulerA))
                .reduce(BigDecimal::add)
                .single();
    }

    //3.groupBy processing batch
    public static Observable<BigDecimal> batch(){
        return Observable
                .just("bread","butter","egg","milk","tomato","cheese"
                        ,"tomato","egg","egg")
                .groupBy(prod -> prod)
                .flatMap(grouped -> grouped
                        .count()
                        .map(quantity -> {
                            String productName = grouped.getKey();
                            return Pair.of(productName,quantity);
                        }))
                .flatMap(order -> purchase(order.getKey() , order.getValue())
                        .subscribeOn(MySchedulers.schedulerA))
                .reduce(BigDecimal::add)
                .single();
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        //async().subscribe(e ->log(Duration.between(start,Instant.now()).toNanos()));
        //sync().subscribe(e ->log(Duration.between(start,Instant.now()).toNanos()));
        batch().subscribe(e ->log(Duration.between(start,Instant.now()).toNanos()));
        //sleep(10,TimeUnit.SECONDS);
    }
}

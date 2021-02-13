package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.BiFunction;

public class CombinationDemo {

    //concat 通过向下游转发接收的元素来链接数据源，先消费发送第一个再消费发送第二个
    static void concat() {
        Flux.concat(
                Flux.just(1, 2, 3),
                Flux.range(4, 2),
                Flux.just(6, 7, 8)
        ).subscribe(System.out::print);
    }

    //merge 将上游的序列数据合并到一个下游序列，与Concat不同，merge是同时的
    static void merge() throws InterruptedException {
        var f1 = Flux.interval(Duration.ofMillis(10)).take(20);
        var f2 = Flux.interval(Duration.ofMillis(20)).take(10);
        Flux.merge(f1,f2).subscribe(System.out::print);
        //Flux.concat(f1,f2).subscribe(System.out::print);
        Thread.sleep(210);
    }

    //zip操作符 将上游所有源的一个元素，组合到一个输出元素中 （取最下元素）
    static void zip() throws InterruptedException {
        var f1 = Flux.interval(Duration.ofMillis(10)).take(20);
        var f2 = Flux.interval(Duration.ofMillis(20)).take(10);
        var f3 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Flux.zip(f1,f2).subscribe(System.out::print);
        f2.zipWithIterable(f3).subscribe(System.out::print);
        //Flux.concat(f1,f2).subscribe(System.out::print);
        Thread.sleep(210);
    }

    //combineLatest 与zip类似 只要有一个元素发送就合并，另一个前一个值
    static void combineLatest() throws InterruptedException {
        var f1 = Flux.interval(Duration.ofMillis(10)).take(20);
        var f2 = Flux.interval(Duration.ofMillis(20)).take(10);
        Flux.combineLatest(f1, f2, new BiFunction<Long, Long, Object>() {
            @Override
            public Object apply(Long aLong, Long aLong2) {
                System.out.print("f1 = " +aLong);
                System.out.print(" f2 = " + aLong2);
                System.out.println(" sum = " + (aLong +aLong2));
                return aLong+aLong2;
            }
        }).subscribe(System.out::println);
        //Flux.concat(f1,f2).subscribe(System.out::print);
        Thread.sleep(210);
    }

    public static void main(String[] args) throws InterruptedException {
        //concat();
        System.out.println();
        //merge();
        //zip();
        combineLatest();
    }
}

package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

import java.util.Arrays;

public class TailorDemo {

    //Flux.any(Predicate) 操作符检查是否至少有一个元素具有所需的属性。
    //hasElements 操作符检查流中是否包含多个元素（短路逻辑）
    //any操作符不仅可以检查元素的相等性，还可以通过Predicate实例来检查其他属性
     static void hasEvenNumber(){
         Flux.just(3,5,7,9,11,19)
                 .any(e-> e%2 == 0)
                 .subscribe(hasEvenNumber -> System.out.println("是否存在偶数： "+ hasEvenNumber));
     }
     //sort 操作符可以在后台对元素排序，在完成后发出已排序的序列
    //自定义裁剪
    static void reduce() {
        Flux.range(1,5)
                .reduce(0, Integer::sum)
                .subscribe(System.out::println);
    }
    //scan能把中间结果发送到下游
    static void scan() {
        Flux.range(1,5)
                .scan(0, Integer::sum)
                .subscribe(System.out::println);
    }
    //利用scan计算移动平均值
    static void average() {
        //定义平均窗口，AI算法中常用这种step
        int bucketSize = 5;
        Flux.range(1,10)
                .index()
                .scan(
                        new int[bucketSize],
                        (acc,elem) -> {
                            //索引用于计算容器acc位置
                            acc[(int) (elem.getT1() % bucketSize)] = elem.getT2();
                            return acc;
                        })
                .skip(bucketSize)//跳过开头元素
                .map(array -> Arrays.stream(array).sum() * 1.0 / bucketSize)
                .subscribe(av -> System.out.println("Running average: " +av));
    }

    //then、thenMany、thenEmpty，在上游完成时完成，这些操作符忽略传入元素，仅重放完成或错误信号，触发新流。
    static void then() {
        Flux.just(1,2,3).thenMany(Flux.just(4,5)).subscribe(System.out::println);
    }

    public static void main(String[] args) {
        //hasEvenNumber();
        //reduce();
        //scan();
        //average();
        then();
    }

}

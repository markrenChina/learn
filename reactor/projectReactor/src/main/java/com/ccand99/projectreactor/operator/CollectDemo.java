package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class CollectDemo {

    public static void main(String[] args) {
        collectSortedList().subscribe(System.out::println);
    }

    //collectList会将Flux处理为Mono<list> 收集操作很耗内存 如果对无限流收集或消耗所有内存。
    //collectMap 映射为Map<k,T>
    //collectMultimap  ->  Map<K,Collection<T>>
    static Mono<List<Integer>> collectSortedList() {
        return Flux.just(8, 1, 5, 6, 7, 2, 3, 4)
                .collectSortedList(Comparator.reverseOrder());
    }

    //repeat() 循环操作
    //defaultIfEmpty(T)  为Flux和Mono提供默认值
    //distinct() 仅传递未在流中遇到的元素。高基数会耗尽内存。可以重载
    //Flux.distinctUntilChanged() 可以适用无限流去重
}

package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

import java.time.Instant;

public class MapDemo {

    public static void main(String[] args) {
        mapDemo();
    }

    /**
     * timestamp()添加时间戳，返回Tuple2，新添加元素在前
     * index()添加索引值，返回Tuple,索引值在前，后一个上一个返回的Tuple2
     */
    static void mapDemo() {
        Flux.range(2018 , 5)
                .timestamp()
                .index()
                .subscribe(
                        e -> System.out.println(
                                "index: " + e.getT1()
                                +", ts: " + Instant.ofEpochMilli(e.getT2().getT1())
                                +", value: " + e.getT2().getT2()
                        )
                );
    }
}

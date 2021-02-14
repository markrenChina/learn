package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;

import java.util.LinkedList;

public class ProcessDemo {

    //buffer 缓冲到容器，结果流的类型为Flux<List<T>>
    //不是每次都需要小请求时，可以buffer合并处理，缓冲提高效率
    static void buffer() {
        Flux.range(1 , 13)
                .buffer(4)
                .subscribe(System.out::println);

    }

    //windowing 开窗 将元素加入Flux<Flux<T>> 流的元素变成另一个流
    static void window() {
        Flux<Flux<Integer>> windowedFlux = Flux.range(101,20)
                .windowUntil(e -> e%3 == 0);

        windowedFlux.subscribe(
                window -> window.collectList().subscribe(System.out::println)
        );
    }

    //group 分组到Flux<GroupedFlux<K,T>>类型的流，一个key对应一个流实例
    static void group() {
        //奇偶分组
        Flux.range(1,7)
                .groupBy(e -> e%2 ==0? "Even" : "Odd")
                .subscribe( groupFlux ->
                        groupFlux.scan(
                                new LinkedList<>(),
                                (list,elem) -> {
                                    list.add(elem);
                                    if (list.size() > 2) {
                                        //超过2个移除前一个
                                        list.remove(0);
                                    }
                                    return list;
                                }
                        ).filter(arr -> !arr.isEmpty())
                        .subscribe(System.out::println)
                );
    }

    //groupJoin 不同时间窗口分组

    public static void main(String[] args) {
        //buffer();
        //buffer响应在完成收集，Window响应在每一次条件达成。
        //window();
        group();
    }
}

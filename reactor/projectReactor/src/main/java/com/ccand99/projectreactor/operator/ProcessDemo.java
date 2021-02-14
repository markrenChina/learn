package com.ccand99.projectreactor.operator;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ProcessDemo {

    //buffer 缓冲到容器，结果流的类型为Flux<List<T>>
    //不是每次都需要小请求时，可以buffer合并处理，缓冲提高效率
    static void buffer() {
        Flux.range(1, 13)
                .buffer(4)
                .subscribe(System.out::println);

    }

    //缓冲还会导致源元素丢失或缓冲区重叠
    static void overlapBuffer() {
        StepVerifier.create(
                Flux.range(1, 10)
                        .buffer(5, 3)
                        .log()//overlapping buffers
        )
                .expectNext(Arrays.asList(1, 2, 3, 4, 5))
                .expectNext(Arrays.asList(4, 5, 6, 7, 8))
                .expectNext(Arrays.asList(7, 8, 9, 10))
                .expectNext(Collections.singletonList(10))
                .verifyComplete();
    }

    //不同于在窗口中，bufferUntil并且bufferWhile不发出空缓冲区
    static void noEmptyBuffer() {
        StepVerifier.create(
                Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                        .bufferWhile(i -> i % 2 == 0)
                        .log()
        )
                .expectNext(Arrays.asList(2, 4, 6)) // triggered by 11
                .expectNext(Collections.singletonList(12)) // triggered by 13
                .verifyComplete();
    }

    //windowing 开窗 将元素加入Flux<Flux<T>> 流的元素变成另一个流
    static void window() {
        Flux<Flux<Integer>> windowedFlux = Flux.range(101, 20)
                .windowUntil(e -> e % 3 == 0);

        windowedFlux.subscribe(
                window -> window.collectList().subscribe(System.out::println)
        );
    }

    //窗口重叠 在window(int maxSize, int skip) 该maxSize参数是在这之后，窗口关闭元件的数量，
    //并且所述skip参数是在被打开一个新的窗口之后，源元件的数量。
    //因此，如果maxSize > skip打开一个新窗口，则在前一个窗口关闭之前，这两个窗口会重叠。
    //使用反向配置（maxSize< skip），将删除源中的某些元素，它们不属于任何窗口。
    static void overlapWindow() {
        StepVerifier.create(
                Flux.range(1, 10)
                        .window(5, 3) //overlapping windows
                        .concatMap(g -> g.defaultIfEmpty(-1)) //show empty windows as -1
        )
                .expectNext(1, 2, 3, 4, 5)
                .expectNext(4, 5, 6, 7, 8)
                .expectNext(7, 8, 9, 10)
                .expectNext(10)
                .verifyComplete();
    }

    //在通过windowUntil和进行基于谓词的窗口化的情况下windowWhile，随后的源元素与谓词不匹配也可能导致空窗口
    static void emptyWindow() {
        StepVerifier.create(
                Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                        .windowWhile(i -> i % 2 == 0)
                        .concatMap(g -> g.defaultIfEmpty(-1))
                        .log()
        )
                .expectNext(-1, -1, -1) //respectively triggered by odd 1 3 5
                .expectNext(2, 4, 6) // triggered by 11
                .expectNext(12) // triggered by 13
                // however, no empty completion window is emitted (would contain extra matching elements)
                .verifyComplete();
    }

    //group 分组到Flux<GroupedFlux<K,T>>类型的流，一个key对应一个流实例
    static void group() {
        //奇偶分组
        Flux.range(1, 7)
                .groupBy(e -> e % 2 == 0 ? "Even" : "Odd")
                .subscribe(groupFlux ->
                        groupFlux.scan(
                                new LinkedList<>(),
                                (list, elem) -> {
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
        //overlapBuffer();
        //noEmptyBuffer();
        emptyWindow();
        //group();
    }
}

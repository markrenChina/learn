package com.ccand99.projectreactor.Thread;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ContextDemo {

    public static void main(String[] args) {
        //threadLocal();
        //context1();
        context2();
    }

    //NullPointerException
     static void threadLocal() {
        ThreadLocal<Map<Object,Object>> threadLocal = new ThreadLocal<>();
        threadLocal.set(new HashMap<>());

         Flux.range(0,10)
                 .doOnNext(k ->
                         threadLocal.get().put(k,new Random(k).nextGaussian()))
                 .publishOn(Schedulers.parallel())
                 .map(k -> threadLocal.get().get(k))
                 .blockLast();
     }

    static void context1() {
        Flux.range(0,10)
                .flatMap( k ->
                        Mono.subscriberContext().doOnNext(contextView -> {
                            Map<Object,Object> map = contextView.get("randoms");
                            map.put(k,new Random(k).nextGaussian());
                            System.out.println("["+k+"]"+"before " + map.get(k));
                        }).thenReturn(k)
                ).publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.subscriberContext().map(contextView -> {
                            Map<Object,Object> map = contextView.get("randoms");
                            System.out.println("["+k+"]"+"later " + map.get(k));
                            return map.get(k);
                        })
                )
                .subscriberContext(context -> context.put("randoms",new HashMap()))
        .blockLast();

    }
    /**
     * Use deferContextual(Function) or transformDeferredContextual(BiFunction) to materialize the context.
     * To obtain the same Mono of Context, use Mono.deferContextual(Mono::just). To be removed in 3.5.0.
     */
    static void context2() {
        Flux.range(0,10)
                .flatMap( k ->
                        Mono.deferContextual(Mono::just)
                                .doOnNext(contextView -> {
                                    Map<Object,Object> map = contextView.get("randoms");
                                    map.put(k,new Random(k).nextGaussian());
                                    System.out.println("["+k+"]"+"before " + map.get(k));
                                }).thenReturn(k)
                ).publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.deferContextual(Mono::just)
                        .map(contextView -> {
                            Map<Object,Object> map = contextView.get("randoms");
                            System.out.println("["+k+"]"+"later " + map.get(k));
                            return map.get(k);
                        }))
                .contextWrite(context -> context.put("randoms",new HashMap()))
                .blockLast();


     }
}

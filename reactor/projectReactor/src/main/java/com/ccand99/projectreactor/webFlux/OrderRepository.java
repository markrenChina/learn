package com.ccand99.projectreactor.webFlux;

import reactor.core.publisher.Mono;


public interface OrderRepository {

    Mono<Order> findById(String id);

    Mono<Order> save(Order order);

    Mono<Void> deleteById(String id);
}

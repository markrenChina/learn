package com.ccand99.projectreactor.sampleWeb.sse;

import reactor.core.publisher.Flux;

public interface StocksService {

    Flux<StockItem> stream();
}
package com.ccand99.awesome;

public interface Processor<T,R> extends Subscriber<T>,Publisher<R> {
}

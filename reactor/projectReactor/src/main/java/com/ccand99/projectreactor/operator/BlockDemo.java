package com.ccand99.projectreactor.operator;

public class BlockDemo {

    //toIterable 将Flux转为阻塞Iterable
    //toStream Flux转为阻塞Stream（Reactor3.2+ 底层调了toIterable）
    //blockFirst 阻塞当前线程，直到上游发出第一个值或完成流为止
    //blockLast 阻塞当前线程，直到上游发出最后一个值或完成流为止，如果onError照常抛出。
    //toIterable，toStream能够用Queue储存先于阻塞完成的事件。
}

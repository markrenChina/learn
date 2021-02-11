package com.ccand99.awesome.rxjava;

import io.reactivex.rxjava3.core.Observable;

public class RxZip {

    public static void main(String[] args) {
        Observable.zip(
                Observable.just("A","B","C"),
                Observable.just("1","2","3"),
                (x,y)-> x + y).subscribe(System.out::println);
    }
}

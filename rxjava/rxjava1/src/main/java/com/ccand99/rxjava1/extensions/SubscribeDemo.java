package com.ccand99.rxjava1.extensions;

import com.ccand99.rxjava1.base.Tweet;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * 订阅 {@link Observer} 返回 {@link Subscription}对象
 * 使用{@link Subscription} 和 {@link rx.Subscriber} 可以控制监听器
 */
public class SubscribeDemo {

    private static final String LF = "=========================================";

    static Observable<Tweet> tweets = Observable.just(
            new Tweet("test"),
            new Tweet("java"),
            new Tweet("test2")
    );

    public static void main(String[] args) {
        demo1();
        demo2();
        demo3();
    }

    //1.
    public static void demo1(){
        tweets.subscribe(
                System.out::println,//onNext
                Throwable::printStackTrace,//onError
                ()-> {System.out.println(LF);} //onCompleted
        );
    }

    //2.
    public static void demo2(){
        Observer<Tweet> observer = new Observer<Tweet>(){
            @Override
            public void onCompleted() {
                System.out.println(LF);
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
            @Override
            public void onNext(Tweet tweet) {
                System.out.println(tweet);
            }
        };
        Subscription subscribe = tweets.subscribe(observer);
        subscribe.unsubscribe();//取消订阅
    }

    /**/

    //3.Subscriber 同时实现了Observer<T>, Subscription
    public static void demo3(){
        Subscriber<Tweet> observer = new Subscriber<Tweet>(){

            @Override
            public void onCompleted() {
                System.out.println(LF);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Tweet tweet) {
                System.out.println(tweet);
                if (tweet.toString().contains("java")){
                    unsubscribe(); //取消订阅
                }
            }
        };
        tweets.subscribe(observer);
    }
}

package com.ccand99.rxjava1.extensions;

import com.ccand99.rxjava1.base.Tweet;
import com.ccand99.rxjava1.base.Utils;
import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.concurrent.TimeUnit;

/**
 * {@link Subject} 示例
 */
public class SubjectDemo {
    private final PublishSubject<Tweet> subject = PublishSubject.create();

    //模拟回调
    private final Listener listener = new Listener() {
        @Override
        public void success(Long l) {
            subject.onNext(new Tweet(l.toString()));
        }

        @Override
        public void error(Throwable t) {
            subject.onError(t);
        }
    };

    //模拟产生回调数据
    private final Subscription mock =
            Observable.interval(1, TimeUnit.SECONDS)
                    .map((Long l) -> {
                        listener.success(l);
                        return l;
                    })
                    .doOnError(listener::error)
                    .subscribe();

    public static void main(String[] args) {
        SubjectDemo subjectDemo = new SubjectDemo();
        /*var lazy = subjectDemo.observe().publish().refCount();
        lazy.subscribe(System.out::println);
        InfiniteDemo.sleep(2,TimeUnit.SECONDS);
        lazy.subscribe(System.out::println);
        InfiniteDemo.sleep(2,TimeUnit.SECONDS);*/
        //publish 产生ConnectableObservable 对象，里面管理了Subscriber集合
        ConnectableObservable<Tweet> published = subjectDemo.observe().doOnNext(System.out::println).publish();
        //ConnectableObservable 如果没有被connect 所有的Subscriber只是在Subscriber集合加入然后搁置
        //connect 会产生一个专用的中间订阅者，所有的订阅都来自这个的共享。
        //搁置订阅，使用connect去触发搁置的订阅，可以确保事件的完整性，可以控制订阅到达的时机（可以看成是同时到达）
        published.connect();
        Utils.sleep(2, TimeUnit.SECONDS);
    }

    //对外暴露subject。可以多次订阅
    public Observable<Tweet> observe() {
        return subject;
    }

    public static interface Listener {
        public void success(Long l);

        public void error(Throwable t);
    }
}

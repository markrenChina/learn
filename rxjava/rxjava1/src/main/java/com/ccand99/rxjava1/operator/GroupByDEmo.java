package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Tweet;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.log;
import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * 对流分组切块。
 * 一个流按照某个特定的条件，下游发生了异步，加锁可以保证一个资源同时被一个事件消费，但是不能保证获取锁的顺序。
 * groupBy能返回一个GroupedObservable<T1,T2>。
 */
public class GroupByDEmo {

    private static ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>(4);

    //模拟一个事件Tweet的流，会定时生成4种Tweet
    private static Observable<Tweet> mock = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map(i ->new Tweet(String.valueOf(i % 4),(int)(i/4L)))
            .take(40);

    public static void main(String[] args) {
        //会出现后面id的跑到前面去执行
        //noGroupBy();
        useGroupBy();
    }

    private static void noGroupBy(){
        mock.subscribe( t ->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    GroupByDEmo.handle(t);
                }
            }).start();
        }
        );
        sleep(60,TimeUnit.SECONDS);
    }

    private static void useGroupBy(){
        var grouped = mock.groupBy(Tweet::toString);
        grouped.subscribe(items -> {
            items.observeOn(Schedulers.newThread()).subscribe(GroupByDEmo::handle);
        });
        sleep(60,TimeUnit.SECONDS);
    }

    //模拟延迟操作
    private static void handle(Tweet tweet){
        Object lock = map.get(tweet.toString());
        if (null == lock) {
            map.put(tweet.toString(), new Object());
            lock = map.get(tweet.toString());
        }
        synchronized (lock){
            //这里我们让第4次进入的执行时间大于后面2次的总和
            var now = Instant.now();
            if (tweet.getId() == 4){
                sleep(3,TimeUnit.SECONDS);
                log(tweet +" id = " + tweet.getId() + " " + Duration.between(now,Instant.now()).toMillis());
            }else {
                sleep(2,TimeUnit.SECONDS);
                log(tweet +" id = " + tweet.getId() + " " + Duration.between(now,Instant.now()).toMillis());
            }
        }
    }
}

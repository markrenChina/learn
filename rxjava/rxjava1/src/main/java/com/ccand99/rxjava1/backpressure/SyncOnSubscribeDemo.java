package com.ccand99.rxjava1.backpressure;

import rx.Observable;
import rx.observables.SyncOnSubscribe;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncOnSubscribeDemo {

    //简单示例
    static Observable.OnSubscribe<Long> onSubscribe =
            SyncOnSubscribe.createStateful(
                    ()-> 0L,//延迟创建一个初始状态
                    (cur,observer) ->{
                        //向下游推送状态，并生成一个新的状态
                        observer.onNext(cur);
                        return cur+1;
                    }
            );
    //=...
    static ResultSet resultSet ;

    //具备错误处理的单状态变量，模拟数据库ResultSet示例
     static Observable.OnSubscribe<Object[]> mockResultSet =
            SyncOnSubscribe.createSingleState(
                    () -> resultSet,
                    (rs,observer) -> {
                        try{
                            rs.next();
                            observer.onNext(toArray(rs));
                        }catch (SQLException e){
                            observer.onError(e);
                        }
                    },
                    rs -> {
                        //会在取消订阅时调用
                        try {
                            //同时关闭Statement,Connection
                            rs.close();
                        }catch (SQLException e){
                            //...
                        }
                    }
            );

    private static Object[] toArray(ResultSet rs) {
         return new Object[0];
    }

    public static void main(String[] args) {
        Observable.create(onSubscribe).subscribe(System.out::println);
    }
}

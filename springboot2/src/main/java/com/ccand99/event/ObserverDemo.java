package com.ccand99.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

/**
 * {@link Observer} 示例
 */
public class ObserverDemo {

    public static void main(String[] args) {
        Observable observable = new Observable() {
            @Override
            protected synchronized void setChanged() {
                super.setChanged();
            }

            @Override
            public void notifyObservers(Object arg) {
                setChanged();
                //super.notifyObservers(arg);
                super.notifyObservers(new EventObject(arg));
                clearChanged();
            }
        };
        //添加 观察者
        observable.addObserver(new EventObserver());
        //发布 事件
        observable.notifyObservers("hello world");
    }

    static class EventObserver implements Observer, EventListener {

        @Override
        public void update(Observable o, Object arg) {
            EventObject eventObject = (EventObject) arg;
            // o 被观察对象， arg 数据
            System.out.println(eventObject);
        }
    }
}

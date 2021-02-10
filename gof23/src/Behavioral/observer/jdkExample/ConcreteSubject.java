package observer.jdkExample;

import java.util.Observable;

//jdk建议观察者使用流（java.util.concurrent）的方式，此方法已弃用
public class ConcreteSubject extends Observable {
    private int state;

    public void set(int state){
        this.state = state; //目标对象的状态发生了改变
        setChanged(); //表示目标对象以及做了更改
        notifyObservers(state);//通知所有的观察者
    }

    public int getState() {
        return state;
    }
}

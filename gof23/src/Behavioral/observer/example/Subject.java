package observer.example;

import java.util.ArrayList;
import java.util.List;

/*
主题对象
 */
public class Subject {

    protected final List<Observer> list = new ArrayList<Observer>();

    public void register(Observer observer){
        list.add(observer);
    }

    public void removeObserver(Observer observer){
        list.remove(observer);
    }

    //通知所有观察者更新状态
    public void notifyAllObserver(){
        list.forEach(e-> e.update(this));
    }
}

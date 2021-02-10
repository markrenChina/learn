package observer.jdkExample;

import java.util.Observable;
import java.util.Observer;

public class ObserverA implements Observer {

    private final String tag ;
    private int myState;

    public ObserverA(String tag) {
        this.tag = tag;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("观察到目标变化"+tag + "状态发送改变");
        myState = ((ConcreteSubject)o).getState();
    }

    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
    }
}

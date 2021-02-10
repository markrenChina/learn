package observer.example;

public class ObserverA implements Observer{

    private final String tag;
    //假设myState需要跟目标对象的state值保持一致
    private int myState;

    public ObserverA(String tag) {
        this.tag = tag;
    }

    @Override
    public void update(Subject subject) {
        System.out.println("观察到目标变化"+tag + "状态发送改变");
        myState = ((ConcreteSubject)subject).getState();
    }

    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
    }
}

package observer.example;

public class ConcreteSubject extends Subject{

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        // 主题状态发生变化是通知所有的观察者
        this.notifyAllObserver();
    }
}

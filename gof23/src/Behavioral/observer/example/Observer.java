package observer.example;

/**
 * 沟通观察者和被观察（实际业务中可能有各种各样的方法）
 */
public interface Observer {
    void update(Subject subject);
}

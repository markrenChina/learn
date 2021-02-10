package observer.example;

public class App {

    public static void main(String[] args) {
        //目标对象
        ConcreteSubject subject = new ConcreteSubject();

        //创建多个观察者
        ObserverA observerA = new ObserverA("A");
        ObserverA observerB = new ObserverA("B");
        ObserverA observerC = new ObserverA("C");

        //让观察者订阅目标对象
        subject.register(observerA);
        subject.register(observerB);
        subject.register(observerC);

        //改变subject状态
        subject.setState(3000);


        System.out.println("******************************");
        System.out.println(observerA.getMyState());
        System.out.println(observerB.getMyState());
        System.out.println(observerC.getMyState());

        subject.setState(1000);


        System.out.println("******************************");
        System.out.println(observerA.getMyState());
        System.out.println(observerB.getMyState());
        System.out.println(observerC.getMyState());
    }
}

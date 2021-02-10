package observer.jdkExample;


/**
 * 顺序不定
 */
public class App {

    public static void main(String[] args) {
        //目标对象
        ConcreteSubject subject = new ConcreteSubject();

        //创建多个观察者
        ObserverA observerA = new ObserverA("A");
        ObserverA observerB = new ObserverA("B");
        ObserverA observerC = new ObserverA("C");

        //让观察者订阅目标对象
        subject.addObserver(observerA);
        subject.addObserver(observerB);
        subject.addObserver(observerC);

        //改变subject状态
        subject.set(3000);


        System.out.println("******************************");
        System.out.println(observerA.getMyState());
        System.out.println(observerB.getMyState());
        System.out.println(observerC.getMyState());

        subject.set(1000);


        System.out.println("******************************");
        System.out.println(observerA.getMyState());
        System.out.println(observerB.getMyState());
        System.out.println(observerC.getMyState());
    }
}

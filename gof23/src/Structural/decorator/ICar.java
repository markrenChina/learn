package decorator;

/**
 * 抽象组件
 */
public interface ICar {
    void move();
}
//具体构建对象
class Car implements ICar{

    @Override
    public void move() {
        System.out.println("陆地上跑！");
    }
}
//装饰器
class SuperCar implements ICar{
    protected final ICar car;

    public SuperCar(ICar car) {
        super();
        this.car = car;
    }

    @Override
    public void move() {
        car.move();
    }
}
//具体装饰对象
class FlyCar extends SuperCar {

    public FlyCar(ICar car) {
        super(car);
    }

    public void fly(){
        System.out.println("天上飞");
    }

    @Override
    public void move() {
        fly();
        super.move();
    }
}
//具体装饰对象
class WaterCar extends SuperCar {

    public WaterCar(ICar car) {
        super(car);
    }

    public void swim(){
        System.out.println("水里游");
    }

    @Override
    public void move() {
        swim();
        super.move();
    }
}

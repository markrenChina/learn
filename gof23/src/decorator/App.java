package decorator;

public class App {

    public static void main(String[] args) {
        Car car = new Car();
        car.move();

        System.out.println();
        //增加飞行功能
        FlyCar flyCar = new FlyCar(car);
        flyCar.move();

        System.out.println();
        WaterCar waterCar = new WaterCar(car);
        waterCar.move();

        //复合组合
        System.out.println();
        WaterCar waterAndFlyCar = new WaterCar(new FlyCar(new Car()));
        waterAndFlyCar.move();
    }
}

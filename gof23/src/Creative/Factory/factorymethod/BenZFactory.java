package Factory.factorymethod;

public class BenZFactory implements CarFactory{
    @Override
    public Car creatCar() {
        return new BenZ();
    }
}

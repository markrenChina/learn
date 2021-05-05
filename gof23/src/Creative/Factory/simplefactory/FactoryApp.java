package Factory.simplefactory;

public class FactoryApp {

    public static void main(String[] args) {
        Car audi = CarFactory.creatCar("Audi");
        Car benZ = CarFactory.creatCar("BenZ");

        assert benZ != null;
        assert audi != null;

        audi.run();
        benZ.run();
    }
}

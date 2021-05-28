package Factory.factorymethod;

public class App {

    public static void main(String[] args) {
        Car audi = new AudiFactory().creatCar();
        Car benZ = new BenZFactory().creatCar();

        audi.run();
        benZ.run();
    }
}

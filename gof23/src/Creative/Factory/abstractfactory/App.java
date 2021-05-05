package Factory.abstractfactory;

public class App {
    public static void main(String[] args) {
        CarFactory luxuryCarFactory = new LuxuryCarFactory();
        Engine engine = luxuryCarFactory.craeteEngine();
        engine.run();
    }
}

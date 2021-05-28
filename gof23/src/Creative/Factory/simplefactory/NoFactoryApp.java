package Factory.simplefactory;

public class NoFactoryApp {

    public static void main(String[] args) {
        Car audi = new Audi();
        Car benz = new BenZ();

        audi.run();
        benz.run();
    }
}

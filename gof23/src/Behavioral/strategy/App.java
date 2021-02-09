package Behavioral.strategy;

public class App {

    public static void main(String[] args) {
        Strategy oldMany = new OldCustomManyStrategy();
        Context context = new Context(oldMany);
        context.printPrice(10000);
    }
}

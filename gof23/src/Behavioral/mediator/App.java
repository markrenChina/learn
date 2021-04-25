package mediator;

public class App {
    public static void main(String[] args) {
        Mediator m = new President();

        Development development = new Development(m);
        Finacial finacial = new Finacial(m);
        development.outAction();
    }
}

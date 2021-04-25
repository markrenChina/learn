package command;

public class App {

    public static void main(String[] args) {
        Command command = new ConcreteCommand(new Receiver());

        Invoke invoke = new Invoke(command);

        invoke.call();

    }
}

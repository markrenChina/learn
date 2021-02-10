package state;

public class App {

    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new BookedState());
        context.setState(new CheckedState());
        context.setState(new FreeState());
    }
}

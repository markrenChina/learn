package state;

public class CheckedState implements State{
    @Override
    public void handle() {
        System.out.println("房间已入住");
    }
}

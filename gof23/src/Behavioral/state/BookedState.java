package state;

public class BookedState implements State{
    @Override
    public void handle() {
        System.out.println("房间已预订，等待入住");
    }
}

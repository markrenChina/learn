package state;

public class FreeState implements State{

    @Override
    public void handle() {
        System.out.println("空闲可以入住");
    }
}

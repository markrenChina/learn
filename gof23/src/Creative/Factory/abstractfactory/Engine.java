package Factory.abstractfactory;

public interface Engine {

    void run();
}

class LuxuryEngine implements Engine {

    @Override
    public void run() {
        System.out.println("跑得快");
    }
}

class LowEngine implements Engine {

    @Override
    public void run() {
        System.out.println("跑的慢");
    }
}

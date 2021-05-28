package Factory.abstractfactory;

public interface Type {

    void revolve();
}

class LuxuryType implements Type {

    @Override
    public void revolve() {
        System.out.println("损耗慢");
    }
}

class LowType implements Type {

    @Override
    public void revolve() {
        System.out.println("损耗快");
    }
}

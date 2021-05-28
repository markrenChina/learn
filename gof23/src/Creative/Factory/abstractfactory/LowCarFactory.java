package Factory.abstractfactory;

public class LowCarFactory implements CarFactory{
    @Override
    public Engine craeteEngine() {
        return new LowEngine();
    }

    @Override
    public Seat craeteSeat() {
        return new LowSeat();
    }

    @Override
    public Type craeteType() {
        return new LowType();
    }
}
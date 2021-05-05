package Factory.abstractfactory;

public class LuxuryCarFactory implements CarFactory{
    @Override
    public Engine craeteEngine() {
        return new LuxuryEngine();
    }

    @Override
    public Seat craeteSeat() {
        return new LuxurySeat();
    }

    @Override
    public Type craeteType() {
        return new LuxuryType();
    }
}

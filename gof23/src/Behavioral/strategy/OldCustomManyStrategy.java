package Behavioral.strategy;

public class OldCustomManyStrategy implements Strategy {
    @Override
    public double getPrice(double standardPrice) {
        System.out.println("老客户大批量，8折");
        return standardPrice*0.8;
    }
}

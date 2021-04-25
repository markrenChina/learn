package strategy;

public class OldCustomFewStrategy implements Strategy {
    @Override
    public double getPrice(double standardPrice) {
        System.out.println("老客户小批量，88折");
        return standardPrice*0.88;
    }
}

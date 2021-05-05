package Factory.simplefactory;

/**
 * 违法开闭原则
 */
public class CarFactory {

    public static Car creatCar(String type) {
        if ("Audi".equals(type)) {
            return new Audi();
        } else if ("BenZ".equals(type)){
            return new BenZ();
        }else {
            return null;
        }
    }
}

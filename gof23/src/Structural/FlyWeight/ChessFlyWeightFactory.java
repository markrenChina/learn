package FlyWeight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 */
public class ChessFlyWeightFactory {

    private static final Map<String,ChessFlyWeight> map = new HashMap<>();

    public static ChessFlyWeight getChess(String color){
        if (map.get(color) != null){
            return map.get(color);
        }else {
            ChessFlyWeight cfw = new ConcreteChess(color);
            map.put(color,cfw);
            return cfw;
        }
    }
}

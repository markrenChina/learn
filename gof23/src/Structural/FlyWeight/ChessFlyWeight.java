package FlyWeight;

/**
 *  享元类
 */
public interface ChessFlyWeight {

    void setColor(String color);
    String getColor();

    void display(Coordinate coordinate);
}

//具体享元类
class ConcreteChess implements ChessFlyWeight {

    private String color;

    public ConcreteChess(String color) {
        this.color = color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void display(Coordinate coordinate) {
        System.out.println(coordinate+"有一颗"+color+"棋子");
    }
}

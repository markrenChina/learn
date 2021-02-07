package FlyWeight;

public class App {
    public static void main(String[] args) {
        ChessFlyWeight chess1 = ChessFlyWeightFactory.getChess("黑色");
        ChessFlyWeight chess2 = ChessFlyWeightFactory.getChess("黑色");

        // 同一个对象
        System.out.println(chess1);
        System.out.println(chess2);

        System.out.println("**********整加额外状态（非共享）");
        chess1.display(new Coordinate(18,18));
        chess2.display(new Coordinate(22,22));

    }
}

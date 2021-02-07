package bridge.use;

/**
 * 使用
 *
 * @author markrenChina
 */
public class App {

    public static void main(String[] args) {
        Pc pc = new LapTopPc(new Lenovo());

        pc.sale();
    }
}

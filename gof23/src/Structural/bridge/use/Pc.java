package bridge.use;


/**
 * 在此目录新增类型
 */
public class Pc {

    protected Brand brand;

    public Pc(Brand brand) {
        this.brand = brand;
    }

    public void sale() {
        brand.sale();
    }
}

class DesktopPc extends Pc {

    public DesktopPc(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("台式机");
    }
}

class LapTopPc extends Pc {
    public LapTopPc(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("笔记本");
    }
}


class PadPc extends Pc {

    public PadPc(Brand brand) {
        super(brand);
    }

    @Override
    public void sale() {
        super.sale();
        System.out.println("平板电脑");
    }
}
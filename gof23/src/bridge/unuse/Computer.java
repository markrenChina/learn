package bridge.unuse;

/**
 * 未使用桥接
 */
public interface Computer {
    void sale();
}

class Desktop implements Computer{
    @Override
    public void sale() {

    }
}


class Laptop implements Computer{
    @Override
    public void sale() {

    }
}
class Pad implements Computer{
    @Override
    public void sale() {

    }
}


class LenovoDesktop extends Desktop{
    @Override
    public void sale() {
        System.out.println("销售联想台式机");
    }
}

class LenovoDeskLaptop extends Laptop {
    @Override
    public void sale() {
        System.out.println("销售联想台式机");
    }
}

class LenovoPad extends Pad{
    @Override
    public void sale() {
        System.out.println("销售联想台式机");
    }
}

class HpDesktop extends Desktop{
    @Override
    public void sale() {
        System.out.println("销售惠普台式机");
    }
}

class HpDeskLaptop extends Laptop {
    @Override
    public void sale() {
        System.out.println("销售惠普台式机");
    }
}

class HpPad extends Pad{
    @Override
    public void sale() {
        System.out.println("销售惠普台式机");
    }
}

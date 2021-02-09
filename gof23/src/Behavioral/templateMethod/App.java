package Behavioral.templateMethod;

public class App {

    public static void main(String[] args) {
        BankTemplateMethod btm = new DrawMoney();
        //btm.process();
        BankTemplateMethod btm2 = new BankTemplateMethod() {
            @Override
            public void transact() {
                System.out.println("理财");
            }
        };
        btm2.process();
    }
}

class DrawMoney extends BankTemplateMethod {

    @Override
    public void transact() {
        System.out.println("取款");
    }
}

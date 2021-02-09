package Behavioral.templateMethod;

public abstract class BankTemplateMethod {

    public void takeNumber() {
        System.out.println("取号");
    }

    public abstract void transact();//子类实现

    public void evaluate() {
        System.out.println("评分");
    }

    public final void process() {
        this.takeNumber();
        this.transact();//钩子
        this.evaluate();
    }
}

package Behavioral.strategy;

/**
 * 复杂和具体策略交互
 * 具体算法和客户端分离，是算法可以独立变化
 * spring可以通过配置文件，动态注入，切换算法
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void printPrice(double price) {
        System.out.println("报价："+strategy.getPrice(price));
    }
}

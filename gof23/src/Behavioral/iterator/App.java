package Behavioral.iterator;

import Behavioral.iterator.ConcreteMyAggregate;
import Behavioral.iterator.MyIterator;

public class App {

    public static void main(String[] args) {
        ConcreteMyAggregate cma = new ConcreteMyAggregate();
        cma.addObject("aaaaa");
        cma.addObject("bbbbb");
        cma.addObject("ccccc");

        MyIterator iter = cma.createIterator();
        while (iter.hasNext()){
            System.out.println(iter.getCurrentObj());
            iter.next();
        }
    }
}

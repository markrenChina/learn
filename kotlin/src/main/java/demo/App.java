package demo;

import lambda.Objkt;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public final class App {

    private static void abc(){}

    public static void main(String[] args) {
//        var s = new Student("mark",18);
//        s.readBook();
//        Singe.INSTANCE.p();
//
//        new Objkt( (Integer) -> {
//            System.out.println(Integer);
//            return null;
//        });
        // f(x) = ax + b
        // f(y) = ay + b
        // f(y) = af(x) + b

        IntStream.range(1,10).forEach(System.out::println);
        Set set = new HashSet<Integer>();
        set.add(1);
        set.add("1223");
    }

    public <T> void add(T i){

    }
}

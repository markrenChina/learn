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

//        IntStream.range(1,10).forEach(System.out::println);
//        Set set = new HashSet<Integer>();
//        set.add(1);
//        set.add("1223");
        String s ;
        s = "world";
        System.out.println(s == "world");
    }

    public <T> void add(T i){

    }

    public void match(char[] text) { // text 是主串
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null) p = root; // 如果没有匹配的，从 root 开始重新匹配
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar == true) {
                    int pos = i-tmp.length+1;
                    System.out.println(" 匹配起始下标 " + pos + "; 长度 " + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }
}

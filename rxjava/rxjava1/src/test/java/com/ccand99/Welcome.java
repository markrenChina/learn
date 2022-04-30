package com.ccand99;

public class Welcome {
    public Welcome() {
    }

    public static void main(String[] args) {
        String[] greeting = new String[]{"com.ccand99.Welcome to Core Java", "by Cay Horstmann", "and Gary Cornell"};

        for (String g : greeting) {
            System.out.println(g);
        }
        var china = new China();
        china.printf();
    }
}

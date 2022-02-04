package com.ccand99.rxjava1.base;

public class Tweet {

    private String msg;

    private int id;

    public Tweet(String msg) {
        System.out.println("create Tweet " + msg);
        this.msg = msg;
    }

    public Tweet(String msg, int id) {
        this.msg = msg;
        this.id = id;
        System.out.println("create Tweet " + msg + " id " + id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return msg;
    }
}

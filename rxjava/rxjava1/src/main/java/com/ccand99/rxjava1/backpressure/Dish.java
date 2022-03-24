package com.ccand99.rxjava1.backpressure;

import java.util.Arrays;

/**
 * 一个盘子对象
 */
public class Dish {
    //模拟一些内存占用
    private final byte[] oneKb = new byte[1_024];
    private final int id;

    public Dish(int id) {
        this.id = id;
        System.out.println("Created: " + id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

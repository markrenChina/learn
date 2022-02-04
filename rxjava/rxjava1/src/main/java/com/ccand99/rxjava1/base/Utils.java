package com.ccand99.rxjava1.base;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static void sleep(int timeOut, TimeUnit unit) {
        try {
            unit.sleep(timeOut);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("InterruptedException");
        }
    }

    public static void log(Object obj){
        System.out.printf("Thread [%s] : %s%n",Thread.currentThread().getName(),obj);
    }

    public static Integer random(int bound){
        return ThreadLocalRandom.current().nextInt(bound);
    }
}

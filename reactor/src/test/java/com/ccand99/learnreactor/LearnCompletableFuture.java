package com.ccand99.learnreactor;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class LearnCompletableFuture {

    public void work1(){
        try {
            Thread.sleep(1000);
            System.out.println("work1 在线程 " +Thread.currentThread().getName() + " 加载完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void work2(){
        try {
            Thread.sleep(2000);
            System.out.println("work2 在线程 " +Thread.currentThread().getName() + " 加载完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void work3(){
        try {
            Thread.sleep(3000);
            System.out.println("work3 在线程 " +Thread.currentThread().getName() + " 加载完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void chainSaync(){
        long time = System.currentTimeMillis();
        CompletableFuture
                .runAsync(this::work1)
                .thenRun(this::work2)
                .thenRun(this::work3)
                .whenComplete((res,throwable) ->{
                    System.out.println("完成 耗时： "+(System.currentTimeMillis() - time));
                    System.out.println("当前线程 "+ Thread.currentThread().getName());
                })
                .join();
    }


    public static void main(String[] args) {
        LearnCompletableFuture learnCompletableFuture = new LearnCompletableFuture();
        learnCompletableFuture.chainSaync();
    }
}

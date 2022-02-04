package com.ccand99.features.event;

public class Executor {
    //ClassFilter

    public void execute(){
        //MethodMatcher： Join Point 方法 需要Pointcut来匹配
        System.out.println("Executing...");
    }
}

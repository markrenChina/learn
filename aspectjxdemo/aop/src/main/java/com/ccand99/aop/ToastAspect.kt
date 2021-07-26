package com.ccand99.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*

/**
 * @author markrenChina
 * aop 处理类
 */
@Aspect
class ToastAspect {

    /**
     * 找切点
     * [* *(..))] 代表任何方法
     */
    //@Pointcut("execution(@com.ccand99.aop.Toast * *(..))")
    //fun toastBehavior(){}


    /**
     * 处理切面
     */
    //@Around("toastBehavior()")
    @Around("execution(@com.ccand99.aspectjxdemo.Toast * *(..))")
    @Throws(Throwable::class)
    fun aroundToast(joinPoint: ProceedingJoinPoint){
        Log.i("TAG", "---------------------------------------------")
        //return joinPoint.proceed();
    }
}
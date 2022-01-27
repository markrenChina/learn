package com.ccand99.features.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * Aspect 配置类
 */
@Aspect
@Order
public class AspectConfiguration {

    //切入点
    @Pointcut(value = "execution(public * *(..))") //匹配Join Point
    private void anyPublicMethod() { // 方法名 = Pointcut名
        //Pointcut 代码体是无效的
        System.out.println("@Pointcut anyPublicMethod");
    }

    /**
     * around生命周期是包裹整个代理，所以需要主动调用被代理对象
     */
    @Around("anyPublicMethod()")
    public Object aroundAnyPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@Around anyPublicMethod");
        //if (ThreadLocalRandom.current().nextBoolean())throw new RuntimeException();
        return pjp.proceed();
    }

    @Before("anyPublicMethod()") //Join Point 拦截动作
    public void beforeAnyPublicMethod() {
        //if (ThreadLocalRandom.current().nextBoolean())throw new RuntimeException();
        System.out.println("@Before anyPublicMethod");
    }

    @AfterReturning("anyPublicMethod()")
    public void afterAnyPublicMethod() {
        System.out.println("@AfterReturning anyPublicMethod");
    }

    @After("anyPublicMethod()")
    public void finalizeAnyPublicMethod() {
        System.out.println("@After anyPublicMethod");
    }

    @AfterThrowing(value = "anyPublicMethod()"
            // ,throwing = "ex"
    )
    public void afterThrowing(
            //RuntimeException ex
    ) {
        System.out.println("@AfterThrowing anyPublicMethod");
    }
}

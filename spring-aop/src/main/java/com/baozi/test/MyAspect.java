package com.baozi.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by baozi on 2018/3/1.
 */
@Aspect
public class MyAspect {
    /**
     * 前置通知
     */
    @Before("execution(* com.baozi.test.UserDao.addUser(..))")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        args[0] = 11;
        System.out.println("前置通知...." + args[0]);
    }

    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "execution(* com.baozi.test.UserDao.addUser(..))", returning = "returnVal")
    public void AfterReturning(Object returnVal) {
        System.out.println("后置通知...." + returnVal);
    }


    /**
     * 环绕通知
     *
     * @param joinPoint 可用于执行切点的类
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.baozi.test.UserDao.addUser(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知前....");
        Object obj = (Object) joinPoint.proceed();
        System.out.println("环绕通知后....");
        return obj;
    }


    /**
     * 无论什么情况下都会执行的方法
     */
    @After(value = "execution(* com.baozi.test.UserDao.addUser(..))")
    public void after() {
        System.out.println("最终通知....");
    }


    /**
     * 抛出通知
     *
     * @param e
     */
    @AfterThrowing(value = "execution(* com.baozi.test.UserDao.addUser(..))", throwing = "e")
    public void afterThrowable(Throwable e) {
        System.out.println("出现异常:msg=" + e.getMessage());
    }
}

package com.wt.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Aspect
public class AOPConfig {

    /**
     *try{
         try{
            //@Before
            method.invoke(..);
         }finally{
            //@After
         }
       //@AfterReturning
     }catch(){
        //@AfterThrowing
     }
     */

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void aopPointCut(){}

    @Around("aopPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object o=null;
//        try {
            Object[] args=proceedingJoinPoint.getArgs();
            System.out.println("AOP---args:"+ Arrays.asList(args));
            o=proceedingJoinPoint.proceed();
            System.out.println("AOP---return: "+o);
//            int a=1/0;
            return o;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return o;
    }

    @AfterThrowing(pointcut = "aopPointCut()",throwing = "ex")
    public void doAfterThrowing(JoinPoint joinpoint, Throwable ex){
        System.out.println("==========afterThrowing==========");
        System.out.println(ex.getMessage());
    }

    @AfterReturning(pointcut = "aopPointCut()",returning = "rvt")
    public Object doAfterReturning(JoinPoint joinPoint,Object rvt){
        System.out.println("==========afterReturning==========,返回值："+rvt);
        return "1";
    }

    @After("aopPointCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("==========after=============");
    }
}

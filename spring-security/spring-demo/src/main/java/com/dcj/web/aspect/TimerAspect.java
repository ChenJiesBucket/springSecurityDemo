package com.dcj.web.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
//但拦截器无法获取当前接口获得的数据 得通过切面实现 aspect 切入点：在哪些方法起作用 在什么时候起作用 增强：起作用时执行的业务逻辑
public class TimerAspect {

   // @Before() 切入点 处理前
    //@AfterThrowing

  //  @After() 切入点 处理后

    //该注解会在包围执行方法 UserController 里面的任何方法都会被拦截
    @Around("execution(* com.dcj.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Long start = System.currentTimeMillis();
        Object[] args= pjp.getArgs();
        for(Object arg :args){
            System.out.println("args is "+args);
        }
        //执行方法 object 返回值
        Object object = pjp.proceed();
        System.out.println("aspect执行时长："+(System.currentTimeMillis()-start));
        System.out.println("Aspect Time End----");
        return  null;
    }

}

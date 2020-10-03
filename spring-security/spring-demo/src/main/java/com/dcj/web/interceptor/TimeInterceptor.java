package com.dcj.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    //处理前方法 return true执行方法 false ；拦截

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) throws Exception {
        System.out.println("preHandle");
        httpServletRequest.setAttribute("start",System.currentTimeMillis());
        HandlerMethod handlerMethod = (HandlerMethod)obj;
        System.out.println(handlerMethod.getBean().getClass().getName());
        System.out.println(handlerMethod.getMethod().getName());
        return true;
    }

    //处理后(前提无异常)执行方法
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandle");

    }

    //方法执行后一定会执行的方法 注意异常处理ControllerAdvice 里面没有该方法执行处理的异常 否则该处无法接收异常
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Long start =(Long)httpServletRequest.getAttribute("start");
        System.out.println("Interceptor执行时长："+(System.currentTimeMillis()-start));
        System.out.println("exception:"+e);
        System.out.println("afterCompletion");

    }
}

package com.dcj.web.filter;

import javax.servlet.*;
import java.io.IOException;
//过滤器 产生有两种方式 1通过@Component 容器存储 2  config
//filter 是javaserlvet 里面的所以只能获取request response 所以无法知道调用spring里面的那些方法 对于
//@Component
public class TimerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("initFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long start = System.currentTimeMillis();
        System.out.println("start:"+start);
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("执行耗时:"+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {
        System.out.println("destroyFilter");
    }
}

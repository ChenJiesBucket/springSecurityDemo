package com.dcj.securityDemo.web.config;

import com.dcj.securityDemo.web.filter.TimerFilter;
import com.dcj.securityDemo.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter /*使用拦截器interceptor 需要使用配置项该类并继承WebMvcConfigurerAdapter*/{
    //当第三方封装的filter 需要实现时 由于没有用@Component 开启只能通过配置方式

    //设置异步拦截器
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
        //configurer.registerCallableInterceptors(); callable方式注册拦截器
        //configurer.registerDeferredResultInterceptors(); DeferredResult方式注册拦截器
        //configurer.setDefaultTimeout();
        //configurer.setTaskExecutor() 设置可重用线程池
    }

    @Autowired
    TimeInterceptor timeInterceptor;

    @Override/*注册拦截器interceptor需要实现的方法*/
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
        //super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean  timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimerFilter timerFilter = new TimerFilter();
        registrationBean.setFilter(timerFilter);
        //设置哪些访问路径需要加上该过滤器
        List<String> urls = new ArrayList<String>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}

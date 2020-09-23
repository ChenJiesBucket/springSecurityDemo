package com.dcj.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//springSecurity 安全配置适配器
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    //实现config 参数为http的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //过滤器链
        //super.configure(http);
        http.formLogin()   //表单登录（指定了身份认证的方式） Username Password AuthFilter
        //http.httpBasic()   //BasicAuth Filter  有一个ExceptionTranslateFilter 接收抛出的异常 最后一步是FilterSecurityIntercepter(决定是否能够通过，根据前面若不通过抛出相应异常)
            .and()         //以及
            .authorizeRequests() //对请求的授权
            .anyRequest()  //的任何请求
            .authenticated(); // 都需要身份认证
    }
}

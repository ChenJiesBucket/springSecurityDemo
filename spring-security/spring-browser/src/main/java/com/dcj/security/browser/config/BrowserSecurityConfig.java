package com.dcj.security.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//springSecurity 安全配置适配器
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

    /**
     * 同来返回加密后的密码(encode) 或者进行验证（mache） 内部写的是加密方法
     * @return
     */
    @Bean
    public  PasswordEncoder passwordEncoder(){
       // return new SCryptPasswordEncoder();
        return new BCryptPasswordEncoder();//密码结果每次不一样 123456加密串 被破解后 所有相同密码也不会被影响
    }

    //实现config 参数为http的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //过滤器链
        //super.configure(http);
        http.formLogin()   //表单登录（指定了身份认证的方式） Username Password AuthFilter
             .successHandler(iAuthenticationSuccessHandler) //登陆成功后的执行的方法
             .failureHandler(iAuthenticationFailureHandler) //登陆失败后的执行的方法
             //.loginPage("/loginPage.html")
                //http.httpBasic()   //BasicAuth Filter  有一个ExceptionTranslateFilter 接收抛出的异常 最后一步是FilterSecurityIntercepter(决定是否能够通过，根据前面若不通过抛出相应异常)
            .and()         //以及
            .authorizeRequests() //对请求的授权
            .anyRequest()  //的任何请求
            .authenticated(); // 都需要身份认证
    }
}

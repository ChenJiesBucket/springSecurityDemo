package com.dcj.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

//之后通过apply放在WebConfig链上
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {//因为是Web方式 所以是HttpSecurity

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //配置filter
        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        //设置AuthenticationManager （从http中获取）
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置成功 失败后的处理方式
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationSuccessHandler(successHandler);

        //配置provider  设置自定义参数userService
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        //设置http  provider  以及 filter(放置在密码校验之后)
        ////把smsCodeAuthenticationFilter过滤器添加在UsernamePasswordAuthenticationFilter之前
        http.authenticationProvider(provider)
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
/*UsernamePasswordAuthenticationFilter拦截登录请求
UsernamePasswordAuthenticationFilter获取到用户名和密码构造一个UsernamePasswordAuthenticationToken传入AuthenticationManager
AuthenticationManager找到对应的Provider进行具体校验逻辑处理(如何找到对应的provider？ 在这里进行配置的provider与filter对应起来即可)
最后登录信息保存进SecurityContext*/

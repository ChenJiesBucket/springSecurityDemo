package com.dcj.security.browser.config;

import com.dcj.security.core.properties.SecurityProperties;
import com.dcj.security.core.validate.code.ValidateCodeFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//springSecurity 安全配置适配器
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private AuthenticationSuccessHandler iAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    /**
     * 同来返回加密后的密码(encode) 或者进行验证（mache） 内部写的是加密方法
     * @return
     */
    @Bean
    public  PasswordEncoder passwordEncoder(){
       // return new SCryptPasswordEncoder();
        return new BCryptPasswordEncoder();//密码结果每次不一样 123456加密串 被破解后 所有相同密码也不会被影响
    }
    /*1 如何使用*loginPage()*方法配置自定义登录页面：
      .loginPage("/login.html")
    2 如果我们不指定这个，Spring Security将在/login上生成一个非常基本的登录表单。
     登录的POST URL  loginProcessingUrl
        触发身份验证默认的URL是  /login，我们可以使用loginProcessingUrl方法来覆盖此URL*/
    //实现config 参数为http的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //过滤器链
        //super.configure(http);
        http .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
             .formLogin()   //表单登录（指定了身份认证的方式） Username Password AuthFilter
             .loginProcessingUrl(securityProperties.getBrowser().getSignForm())//首先导致403 forbidden的原因是 Spring Security默认开启了csrf 保护，但是login-page（自定义的登录页面，网上大部分的例子都是页面中只有username,password这两个input输入框，所以自定义login-processing-url时，碰到这个问题很正常） 缺少token（用来防止csrf攻击的一种方式吧），所以被禁止访问
             .failureHandler(iAuthenticationFailureHandler) //登陆失败后的执行的方法
             .successHandler(iAuthenticationSuccessHandler) //登陆成功后的执行的方法
             .loginPage(securityProperties.getBrowser().getLoginPage())
                //http.httpBasic()   //BasicAuth Filter  有一个ExceptionTranslateFilter 接收抛出的异常 最后一步是FilterSecurityIntercepter(决定是否能够通过，根据前面若不通过抛出相应异常)
            .and()         //以及
            .authorizeRequests() //对请求的授权
            .antMatchers("/code/image",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignForm()
            ).permitAll()
            .anyRequest()  //的任何请求
            .authenticated()
        .and().csrf().disable(); // 都需要身份认证
    }
}

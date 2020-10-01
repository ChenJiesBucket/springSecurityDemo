package com.dcj.security.browser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//需要放到容器中！！！
@Component
public class MyUserDetailService  implements UserDetailsService {

     private Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

     @Autowired
     private  PasswordEncoder passwordEncoder;
    @Override
    //不一定是 UserDetails 可能是实现类
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //根据用户名查询用户信息
        logger.info("登录用户名{}",username);

       // return new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        //几个true的意义 都要通过
        //enabled;是否可用（逻辑删除）
        //accountNonExpired;账号登录是否过期
        //credentialsNonExpired;密码是否过期  有的网站需要定时更换密码
        //accountNonLocked;账号是否被锁定
        return new User(username,passwordEncoder.encode("123456"),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}

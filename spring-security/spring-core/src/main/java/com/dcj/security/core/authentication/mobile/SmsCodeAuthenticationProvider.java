package com.dcj.security.core.authentication.mobile;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

//实现短信身份认证检测逻辑
/*自定义一个SmsCodeAuthenticationProvider处理登录逻辑并返回经过认证的用户信息，
通过重写supports方法实现向AuthenticationManager传入SmsCodeAuthenticationToken时
使用SmsCodeAuthenticationProvider处理认证*/
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());
        if(user==null){
            throw  new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        authenticationTokenResult.setDetails(authenticationToken.getDetails());
        return authenticationTokenResult;
    }

   /* @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }*/


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}

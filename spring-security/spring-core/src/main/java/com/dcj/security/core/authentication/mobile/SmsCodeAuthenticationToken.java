package com.dcj.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
//仿照 org.springframework.security.web.authentication 下的 SmsCodeAuthenticationToken 编写
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 4208889L;
    private final Object principal;
    private Object credentials;//里面存储用户密码 短信登陆不需要 去掉

    //登陆前
    public SmsCodeAuthenticationToken(Object mobile) {
        super((Collection)null);
        this.principal = mobile;//存放手机号 登陆后用户名
        this.setAuthenticated(false);
    }

    //登陆后
    public SmsCodeAuthenticationToken(Object username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = username;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}

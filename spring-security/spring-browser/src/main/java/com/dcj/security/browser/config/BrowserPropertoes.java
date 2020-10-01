package com.dcj.security.browser.config;

public class BrowserPropertoes {
    private String loginPage = "loginPage.html";

    private LoginType loginType = LoginType.JSON;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}

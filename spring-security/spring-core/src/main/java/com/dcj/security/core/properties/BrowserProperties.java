/**
 *
 */
package com.dcj.security.core.properties;

/**
 * @author zhailiang
 *
 */
public class BrowserProperties {

	private SessionProperties session = new SessionProperties();

	private String signUpUrl = "/imooc-signUp.html";

	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

	private String signForm = SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM;

	private String mobilesignForm = SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE;

	private LoginResponseType loginType = LoginResponseType.JSON;

	private Boolean openFilter = true;

	private int rememberMeSeconds = 3600;

	public String getLoginPage() {
		return loginPage;
	}

	public String getSignForm() {
		return signForm;
	}

	public void setSignForm(String signForm) {
		this.signForm = signForm;
	}

	public Boolean getOpenFilter() {
		return openFilter;
	}

	public void setOpenFilter(Boolean openFilter) {
		this.openFilter = openFilter;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginResponseType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginResponseType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

	public SessionProperties getSession() {
		return session;
	}

	public void setSession(SessionProperties session) {
		this.session = session;
	}


	public String getMobilesignForm() {
		return mobilesignForm;
	}

	public void setMobilesignForm(String mobilesignForm) {
		this.mobilesignForm = mobilesignForm;
	}
}

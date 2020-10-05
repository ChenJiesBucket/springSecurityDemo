package com.dcj.security.core.validate.code;

import com.dcj.security.core.properties.SecurityProperties;
import com.dcj.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 * @Author dcj
 * @Description  检测验证码
 * @Date 2020/10/4 21:25
 * @Param
 * @return
**/

//OncePerRequestFilter spring 提供的工具类 每次请求只能调用一次
//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
//从结果可以看出，在Spring初始化bean的时候，如果该bean实现了InitializingBean接口，并且同时在配置文件中指定了init-method，系统则是先调用afterPropertieSet()方法，然后再调用init-method中指定的方法。
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();



    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
        for(String url:configUrls){
            urls.add(url);
        }
        urls.add(securityProperties.getBrowser().getSignForm());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
         AntPathMatcher  antPathMatcher= new AntPathMatcher();
        boolean antMantch = false;
        for(String url: urls){
            //spring路径匹配工具只要其中一个和当前路径匹配即可
            if(antPathMatcher.match(url,httpServletRequest.getRequestURI())){
                antMantch = true;
            }
        }
       // if(StringUtils.equals(securityProperties.getBrowser().getSignForm(),httpServletRequest.getRequestURI())
        if(antMantch
        && StringUtils.equals(httpServletRequest.getMethod(),"POST")){
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException exp) {
                iAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse,exp);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {

        ImageCode codeInSession=(ImageCode) sessionStrategy.getAttribute(request,

                ValidateController.SESSION_KEY);

                String codeInRequest=ServletRequestUtils.getStringParameter(request.getRequest() , "imageCode") ;

        if(StringUtils.isBlank(codeInRequest) ) {

            throw new ValidateCodeException("验证码的值不能为空") ;

        }

        if(codeInSession==null) {

            throw new ValidateCodeException("验证码不存在") ;

        }

        if(codeInSession.isExpried()) {

            sessionStrategy.removeAttribute(request, ValidateController.SESSION_KEY);

            throw new ValidateCodeException("验证码已过期") ;

        }

        if(!StringUtils.equals(codeInSession.getCode() , codeInRequest) ) {

            sessionStrategy.removeAttribute(request,ValidateController.SESSION_KEY) ;
            throw new ValidateCodeException("验证码不匹配") ;
        }
    }
}


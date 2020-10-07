package com.dcj.security.core.validate.code;

import com.dcj.security.core.properties.SecurityConstants;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler iAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();



    private Set<String> urls = new HashSet<>();


    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
       /* String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
        for(String url:configUrls){
            urls.add(url);
        }
        urls.add(securityProperties.getBrowser().getSignForm());*/

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    /*@Override
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
    }*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         AntPathMatcher  antPathMatcher= new AntPathMatcher();

        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                iAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
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


package com.dcj.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;
/**
 * @Author dcj
 * @Description 验证码生成接口
 * @Date 2020/10/5 15:54
 * @Param
 * @return
**/
public interface ValidateCodeGenerator {
     ValidateCode generate(ServletWebRequest request);
}

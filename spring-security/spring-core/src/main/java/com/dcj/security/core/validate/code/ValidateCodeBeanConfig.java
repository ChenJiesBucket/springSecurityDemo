package com.dcj.security.core.validate.code;

import com.dcj.security.core.properties.SecurityProperties;
import com.dcj.security.core.validate.code.image.ImageCodeGenerator;
import com.dcj.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.dcj.security.core.validate.code.sms.SmsCodeGenerator;
import com.dcj.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//为了后来方便对代码进行重写 编写验证码配置类
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name="imageCodeGenerator")
    //在系统找不到@Component 为imageCodeGenerator的class时 执行以下代码
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name="smsCodeGenerator")
    //在系统找不到@Component 为imageCodeGenerator的class时 执行以下代码
    public ValidateCodeGenerator smsCodeGenerator(){
        SmsCodeGenerator codeGenerator = new SmsCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * @Author dcj
     * @Description  短信验证码
     * @Date 2020/10/5 15:50
     * @Param
     * @return
    **/
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}

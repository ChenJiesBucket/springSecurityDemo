package com.dcj.security.core.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSmsCodeSender implements SmsCodeSender {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info("给手机号为{}发送了验证码-{}",mobile,code);
    }

}

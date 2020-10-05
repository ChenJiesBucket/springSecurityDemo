package com.dcj.security.core.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SmsCodeSender  {

    public void send(String mobile,String code);
}

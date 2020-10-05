package com.dcj.security.core.validate.code;

import com.dcj.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateController {
     private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
     public  static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";


    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private  ValidateCodeGenerator smsCodeGenerator;


     /*@GetMapping("/code/image")
     public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
         ImageCode imageCode = createImageCode(request,response);
         sessionStrategy.setAttribute(new ServletRequestAttributes(request),SESSION_KEY,imageCode);
         ImageIO.write(imageCode.getBufferedImage(),"JPEG",response.getOutputStream());

     }

    private ImageCode createImageCode(HttpServletRequest request,HttpServletResponse response) {
         ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
         return imageCode;
    }*/

    @Autowired
    private SmsCodeSender smsCodeSender;


    @GetMapping("/code/{type}")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        /*ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletRequestAttributes(request),SESSION_KEY,smsCode);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        smsCodeSender.send(mobile,smsCode.getCode());*/
    }



}

package com.dcj.security.core.validate.code;

import com.dcj.security.core.validate.code.image.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateController {
     private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
     public  static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

     @Autowired
     private  ValidateCodeGenerator imageCodeGenerator;


     @GetMapping("/code/image")
     public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
         ImageCode imageCode = createImageCode(request,response);
         sessionStrategy.setAttribute(new ServletRequestAttributes(request),SESSION_KEY,imageCode);
         ImageIO.write(imageCode.getBufferedImage(),"JPEG",response.getOutputStream());

     }

    private ImageCode createImageCode(HttpServletRequest request,HttpServletResponse response) {
         ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
         return imageCode;
    }

}

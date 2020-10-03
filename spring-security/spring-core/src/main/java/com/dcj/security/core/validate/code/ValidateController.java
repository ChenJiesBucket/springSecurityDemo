package com.dcj.security.core.validate.code;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateController {
     private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
     private  static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";


     @GetMapping("/code/image")
     public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
         ImageCode imageCode = createImageCode(request);
         sessionStrategy.setAttribute(new ServletRequestAttributes(request),SESSION_KEY,imageCode);
         ImageIO.write(imageCode.getBufferedImage(),"JPEG",response.getOutputStream());

     }

    private ImageCode createImageCode(HttpServletRequest request) {
         ImageCode imageCode =  new ImageCodeGenerator().generate();
         return imageCode;
    }

}

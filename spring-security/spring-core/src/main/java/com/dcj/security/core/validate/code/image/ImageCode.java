/**
 *
 */
package com.dcj.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.dcj.security.core.validate.code.ValidateCode;


/**
 *
 * 图形验证码与短信验证码只相差图片数据
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn){
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}

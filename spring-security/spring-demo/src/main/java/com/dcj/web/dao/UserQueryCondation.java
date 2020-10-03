package com.dcj.web.dao;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;


public class UserQueryCondation {

    @NotBlank
    @ApiModelProperty("用户名")
    private String uswername;

    @ApiModelProperty("用户年龄")
    private int age;

    public String getUswername() {
        return uswername;
    }

    public void setUswername(String uswername) {
        this.uswername = uswername;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

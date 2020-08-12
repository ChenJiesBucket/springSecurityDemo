package com.dcj.securityDemo.web.dao;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class UserQueryCondation {

    @NotBlank
    private String uswername;

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

package com.dcj.securityDemo.web.controller;


import com.dcj.securityDemo.web.dao.User;
import com.dcj.securityDemo.web.dao.UserQueryCondation;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @RequestMapping(value="/user",method = RequestMethod.GET)
    public List<User> query(UserQueryCondation condation, @PageableDefault Pageable pageable){/*spring data limian*/
        //利用反射解析bean
        System.out.println(ReflectionToStringBuilder.toString(condation, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageNumber()+"----"+ pageable.getPageSize()+"----"+pageable.getSort());

        List<User> list = new ArrayList();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }
    //requestBody 用户将content里面字符串数据转为对象
    @PostMapping(value="/useradd") //用hibernate valdate 进行数据校验  bindingResult 进行数据
    public List<User> createUser(@Valid @RequestBody UserQueryCondation condation, BindingResult errors ){/*spring data limian*/
        //利用反射解析bean
        System.out.println(ReflectionToStringBuilder.toString(condation, ToStringStyle.MULTI_LINE_STYLE));
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach((error)->{
                System.out.println(error.getDefaultMessage());
            });
        }

        List<User> list = new ArrayList();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }
}

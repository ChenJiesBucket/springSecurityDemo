package com.dcj.web.controller;


import com.dcj.web.dao.User;
import com.dcj.web.dao.UserQueryCondation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation("查询用户")
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
    @ApiOperation("新增用户")
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

    @GetMapping("/userInfo/{id:\\d+}")
    public User getInfo(@PathVariable @ApiParam("用户查询id") String id){
        System.out.println("进入getInfo");
        User user = new User();
        user.setUsername("Tom");
        return user;
    }
}

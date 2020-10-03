package com.dcj.web.controller;

import com.dcj.web.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

//用于处理controller 异常注解
@ControllerAdvice
public class ControllerExceptionHandler  {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody//用于返回数据转为json
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handlerUserNotExistException(UserNotExistException ex){
        HashMap result = new HashMap();
        result.put("name",ex.getUserName());
        result.put("age",ex.getAge());
        result.put("message",ex.getMessage());
        return result;
    }
}

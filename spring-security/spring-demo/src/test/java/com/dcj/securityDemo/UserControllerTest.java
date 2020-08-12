package com.dcj.securityDemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
//注意测试路径要与 main 下java 路径一致！
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
//测试环境伪造Mvc环境
    @Autowired
    private WebApplicationContext wac;

    //@RestController RequestMapping及其变体 映射http请求url到java方法
    //@RequestParam 映射请求参数到java方法的参数  name 指定名字  defaultValue 默认值  required 是否必填 默认true
    //@Pageabledefault 分页参数的默认值
    private MockMvc mockMvc;

    //调用接口前生成
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        //创造请求路径
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("uswername","demo")
                .param("age","11")
                .contentType(MediaType.APPLICATION_JSON_UTF8) //applicationcontext类型
        )//期望获取的参数 返回ok状态
                .andExpect(MockMvcResultMatchers.status().isOk())
                //期望请求返回集合长度是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void validUser() throws Exception {
        //创造请求路径\
        String content =  "{\"uswername\":null,\"age\":\"11\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/useradd")
                .content(content)  //requestBody
                .contentType(MediaType.APPLICATION_JSON_UTF8) //applicationcontext类型
        )//期望获取的参数 返回ok状态
                .andExpect(MockMvcResultMatchers.status().isOk())
                //期望请求返回集合长度是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

}

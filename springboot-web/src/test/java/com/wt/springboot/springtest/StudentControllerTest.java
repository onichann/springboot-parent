package com.wt.springboot.springtest;

import com.alibaba.fastjson.JSON;
import com.wt.springboot.SpringbootWebApplication;
import com.wt.springboot.springtest.bean.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SpringbootWebApplication.class})
public class StudentControllerTest {

    // 注入Spring容器
    @Autowired
    private WebApplicationContext wac;
    // MockMvc实现了对Http请求的模拟
    private MockMvc mvc;
    @Before
    public void setupMockMvc(){
        // 初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 新增学生测试用例
     * @throws Exception
     */
    @Test
    public void addStudent() throws Exception{
//        String json="{\"name\":\"孙悟空\",\"address\":\"花果山\",\"age\":\"700\",\"sex\":\"男\"}";
        Student student = new Student().setName("孙悟空").setAddress("花果山").setAge(700).setSex('男');
        mvc.perform(MockMvcRequestBuilders.post("/student/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(student).getBytes()) //传json参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 获取学生信息测试用例
     * @throws Exception
     */
    @Test
    public void qryStudent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/student/get/4")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(700))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("花果山"))
                .andDo(MockMvcResultHandlers.print());
    }
}
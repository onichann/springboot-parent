package com.wt.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wt.springboot.common.IDUtils;
import com.wt.springboot.common.IPUtil;
import com.wt.springboot.config.EnvConfig;
import com.wt.springboot.config.PropConfig;
import com.wt.springboot.exception.SpringWebException;
import com.wt.springboot.mybatis.mapper.UserXmlMapper;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.pojo.Student;
import com.wt.springboot.pojo.User_Mybatis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
//@EnableConfigurationProperties(ConfigProperties.class)
public class IndexController  {

    private static Log log = LogFactory.getLog(IndexController.class);

    @Autowired
    private PropConfig configProperties;

    @Autowired
    private EnvConfig envConfig;

    @Value("${test.url}")
    private String url;

    /**
     * uploadDir2不存在则替换成
     */
    @Value("${uploadDir2:'http://localhost:9999/test'}")
    private String uploadDir;

    /**
     * message属性存在则大写
     */
    @Value("#{returnJson.message?.toUpperCase()}")
    private  String message;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/login2}")
    public String login(){
        return "login2";
    }


    @RequestMapping("/json")
    @ResponseBody
    public String json(HttpServletResponse response){
        response.setHeader("token", IDUtils.getUUID());
        String str=configProperties.getUrl()+"||"+uploadDir+"||"+url+"||"+envConfig.getJAVA_HOME()+"||中文";
        log.debug(str);
        return str;
    }

    @RequestMapping("/json2")
    @ResponseBody
    public String tojson(@Autowired ReturnJson returnJson){
        return JSON.toJSONString(returnJson, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty);
    }

    @RequestMapping("/getIp")
    @ResponseBody
    public String getIp(HttpServletRequest request) {
//        if(1>0) throw new SpringWebException("测试异常");
        System.out.println("111111111");
        return IPUtil.getIpAddr(request);
    }

    @RequestMapping("/json3")
    public @ResponseBody Model json3(Model model, ModelAndView modelAndView,
                                     @RequestParam(name = "id",required = false,defaultValue = "default") String Id,
                                     Student student, @Autowired ReturnJson returnJson){
        model.addAttribute("attr");
//        model.addAllAttributes() //变量纯在则覆盖
//        model.mergeAttributes()//变量存在则忽略
        modelAndView.addObject("key","value").setViewName("/test.html");
        return model;
    }

//    @ModelAttribute
//    public void findUserById(@PathVariable String id,Model model){
//        model.addAttribute("user","user");
//    }
//
//    @GetMapping("/{id}/get.json")
//    @ResponseBody
//    public String getUser(Model model){
//        boolean is=model.containsAttribute("user");
//        return "contains:"+is;
//    }


    //拓展绑定的特性 /printDate?date=1999-01-01
    @InitBinder
    protected  void  initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @GetMapping("/printDate")
    @ResponseBody
    public Object printDate(Date date){
        System.out.println(date);
        return date;
    }


    /**
     * 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(SpringWebException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String errorHandler(Exception e){
        return e.getMessage();
    }

    /**
     * 这个方法有问题???--解决需要将src下的.xml编译过去 pom.xml-- build--resource
     */
    @Autowired
    private UserXmlMapper userXmlMapper;

    @ResponseBody
    @RequestMapping("/getMyUesr")
    public List<User_Mybatis> query(){
        return userXmlMapper.queryUsers();
    }

    /**
     * @see MappingJackson2JsonView
     * @return
     */
    @RequestMapping("/json4")
    public Object json4(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        modelAndView.addObject("user","test我");
        return modelAndView;
    }
}
package com.wt.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wt.springboot.config.EnvConfig;
import com.wt.springboot.config.PropConfig;
import com.wt.springboot.exception.SpringWebException;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.utils.IDUtils;
import com.wt.springboot.utils.IPUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class IndexController  {

    private static Log log = LogFactory.getLog(IndexController.class);

    @Autowired(required = false)
    private PropConfig configProperties;

    @Autowired
    private EnvConfig envConfig;

    /**
     * uploadDir2不存在则替换成
     */
    @Value("${uploadDir:'http://localhost:9999/test'}")
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


    @RequestMapping("/json")
    @ResponseBody
    public void json(HttpServletRequest request,HttpServletResponse response,@Autowired ReturnJson returnJson){
        response.setHeader("token", IDUtils.getUUID());
        String jsonString = JSON.toJSONString(returnJson, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
        String ipAddr = IPUtil.getIpAddr(request);
        String str=configProperties.getUrl()+"||"+uploadDir+"||"+envConfig.getJAVA_HOME()+ "||中文";
    }

    //拓展绑定的特性 /printDate?date=1999-01-01
    @InitBinder
    protected  void  initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @GetMapping("/printDate")
    @ResponseBody
    public Object printDate(Date date){
        return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
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
     * @see MappingJackson2JsonView
     * @return
     */
    @RequestMapping("/json2")
    public Object json2(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        modelAndView.addObject("user","test我");
        return modelAndView;
    }
}

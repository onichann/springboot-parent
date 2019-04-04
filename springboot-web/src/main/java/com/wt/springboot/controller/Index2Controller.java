package com.wt.springboot.controller;


import com.wt.springboot.pojo.ReturnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/index2")
public class Index2Controller {

    @Autowired
    private ReturnJson returnJson;

    @Autowired
    private ReturnJson returnJson2;

//    @InitBinder
//    protected  void  initBinder(WebDataBinder binder){
//        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
//    }

    @RequestMapping("/test1")
    public Object test1(@RequestParam("date") Date date, @Autowired  ReturnJson returnJson){
        Date now= Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("date",date);
        returnJson.setData(map);
        return returnJson;
    }

    @RequestMapping("/test2")
    public Object test2(@Autowired ReturnJson returnJson){
        return "200";
    }

    @RequestMapping(value = "/test3",headers ="version=1.0" )
    public Object test(ReturnJson returnJson){
        return "200";
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("/jsp/jsplogin");
    }
}

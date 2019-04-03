package com.wt.springboot.exception;


import com.wt.springboot.common.SpringContextUtil;
import com.wt.springboot.pojo.ReturnJson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages = "com.wt.controller",annotations = {Controller.class, RestController.class})
public class BaseControllerAdvice {
    private static Log log = LogFactory.getLog(BaseControllerAdvice.class);

    @ExceptionHandler(value = Exception.class)   //处理并相应请求,进行返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //ResponseStatus修饰目标方法，无论它执行方法过程中有没有异常产生，
    // 用户都会得到异常的界面。并且为异常提供一个响应码
    @ResponseBody
    public ReturnJson exception(Exception e, WebRequest request, HttpServletResponse response) throws Exception {
        log.error(e.getMessage());
//        response.sendRedirect("jsp/errorPage/500.jsp");
//        return "redirect:errorPage/500.jsp";
        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }
        ReturnJson returnJson = SpringContextUtil.getCtx().getBean("returnJson", ReturnJson.class);
        returnJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        returnJson.setData(e.toString());
        returnJson.setMessage("系统内部错误，请求访问失败");
        returnJson.setSuccess(false);
        log.info(returnJson);
        return returnJson;
    }

}

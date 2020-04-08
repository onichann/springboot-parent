package com.wt.springboot.validator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wt.springboot.pojo.ReturnJson;
import com.wt.springboot.validator.pojo.ValidateBean;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("/validate")
@Validated  //验证方法参数开启
public class ValidateController {

    /**
     * @Valid JSR303 验证
     * @param validateBean
     * @return
     */
    @RequestMapping("/find")
    public String findBean(@Valid ValidateBean validateBean){
        return JSON.toJSONString(validateBean, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("/find2")
    public String findBean2(@Validated(value = ValidateBean.update.class) ValidateBean validateBean){
        return JSON.toJSONString(validateBean, SerializerFeature.WriteMapNullValue);
    }

    //spring实现对方法参数的校验
    @RequestMapping("/check")
    public String checkParam(@Length(min = 5,message = "name 最小为5位") @NotEmpty(message = "name 不能为空") @NotNull(message = "name 不能为null") @RequestParam(value = "name",required = false) String name,
                             @Range(min = 1,max = 10,message = "age 1-10之间")  @NotNull(message = "age 不能为null") @Min(0) @RequestParam(value = "age",defaultValue = "0",required = false) int age){
        return name+"||"+age;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandler(Exception e){
        return e.toString();
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public String handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "||");
        }
        return strBuilder.toString();
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class,ConstraintViolationException.class })
    public ReturnJson handleResourceNotFoundException(Exception e) {
        StringBuilder strBuilder = new StringBuilder();
        if(e instanceof MethodArgumentNotValidException){
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            if (bindingResult.hasErrors()) {
                for (ObjectError error : bindingResult.getAllErrors()) {
                    strBuilder.append(error.getDefaultMessage()).append(",");
                }
            }
        }else if(e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)e).getConstraintViolations();
            for (ConstraintViolation<?> violation : violations ) {
                strBuilder.append(violation.getMessage()).append(",");
            }
        }
        return new ReturnJson().setCode(HttpStatus.BAD_REQUEST.value()).setData(strBuilder.toString());
    }

}

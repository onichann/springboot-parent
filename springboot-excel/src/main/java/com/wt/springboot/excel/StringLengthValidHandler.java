package com.wt.springboot.excel;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Objects;

public class StringLengthValidHandler extends ValidHandler {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
          return Length.class;
    }


    @Override
    public ValidResponse validValue(ValidRequest request) {
        Object value=request.getCellBean().getCellValue();
        Field field = request.getField();
        if(!StringUtils.isEmpty(value)&& Objects.equals("java.lang.String",field.getType().getName())){
            Length length = field.getDeclaredAnnotation(Length.class);
            int max = length.max();
            if(Objects.toString(value).length()>max){
                return new ValidResponse().setSuccess(false).setErrorMessage(MessageFormat.format("列 {0} 长度过长,{1} 默认长度为{2}",field.getName(),length.message(),max));
            }
        }
        return new ValidResponse().setSuccess(true).setErrorMessage("字符串长度检查通过！");
    }
}

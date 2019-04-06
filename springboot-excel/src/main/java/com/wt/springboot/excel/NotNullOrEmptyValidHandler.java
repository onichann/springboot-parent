package com.wt.springboot.excel;

import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;

public class NotNullOrEmptyValidHandler extends ValidHandler {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
          return NotNullOrEmpty.class;
    }


    @Override
    public ValidResponse validValue(ValidRequest request) {
        Object value=request.getCellBean().getCellValue();
        Field field = request.getField();
        if(ObjectUtils.isEmpty(value)){
            NotNullOrEmpty notNullOrEmpty = field.getDeclaredAnnotation(NotNullOrEmpty.class);
            return new ValidResponse().setSuccess(false).setErrorMessage(MessageFormat.format("列 {0} 不能为空,{1}",field.getName(),notNullOrEmpty.message()));
        }
        return new ValidResponse().setSuccess(true).setErrorMessage("非空检查通过！");
    }
}

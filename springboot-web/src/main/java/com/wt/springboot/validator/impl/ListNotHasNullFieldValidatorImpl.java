package com.wt.springboot.validator.impl;


import com.wt.springboot.annotation.ListNotHasNullField;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListNotHasNullFieldValidatorImpl implements ConstraintValidator<ListNotHasNullField,List> {
    private int value;

    @Override
    public void initialize(ListNotHasNullField constraintAnnotation) {
        this.value=constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        if(CollectionUtils.isEmpty(list)){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("ERROR TYPE LIST FIELD  IS NULL").addConstraintViolation();
            return false;
        }
        for (Object object : list) {
            if (object == null) {
                //如果List集合中含有Null元素，校验失败
                return false;
            }
        }
        return true;
    }

}

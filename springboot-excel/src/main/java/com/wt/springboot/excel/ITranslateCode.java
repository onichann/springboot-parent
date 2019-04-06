package com.wt.springboot.excel;

import org.springframework.util.ObjectUtils;

public interface ITranslateCode {

    String translate(Object key);

    static String simpleTranslate(Object key){
        return ObjectUtils.nullSafeToString(key);
    }
}

package com.wt.springboot.dingding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.Assert;

@SpringBootApplication
public class SpringbootDingdingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDingdingApplication.class, args);
    }





    public PropertyMapper alwaysApplyingWhenNonNull() {
        //            @Override
//            public <T> PropertyMapper.Source<T> apply2(PropertyMapper.Source<T> source) {
//                return xxx(source);
//            }
        return this.alwaysApplying(this::xxx);
    }




    public <T> PropertyMapper.Source<T> xxx(PropertyMapper.Source<T> source) {
        return source.whenNonNull();
    }

//    private <T> PropertyMapper.Source<T> whenNonNull(PropertyMapper.Source<T> source) {
//        return source.whenNonNull();
//    }



    public PropertyMapper alwaysApplying(SourceOperator operator) {
        Assert.notNull(operator, "Operator must not be null");
        return null;
    }

//    @FunctionalInterface
    public interface SourceOperator {
        <T> PropertyMapper.Source<T> apply(PropertyMapper.Source<T> source);
//        <T> PropertyMapper.Source<T> apply2(PropertyMapper.Source<T> source);
    }

}

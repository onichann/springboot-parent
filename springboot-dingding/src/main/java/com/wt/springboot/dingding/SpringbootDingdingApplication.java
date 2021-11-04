package com.wt.springboot.dingding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.util.Assert;

@SpringBootApplication
@EnableKafka
public class SpringbootDingdingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDingdingApplication.class, args);
    }





    public PropertyMapper alwaysApplyingWhenNonNull() {
        return this.alwaysApplying(this::whenNonNull);
    }



    private <T> PropertyMapper.Source<T> whenNonNull(PropertyMapper.Source<T> source) {
        return source.whenNonNull();
    }



    public PropertyMapper alwaysApplying(SourceOperator operator) {
        Assert.notNull(operator, "Operator must not be null");
        return null;
    }

    @FunctionalInterface
    public interface SourceOperator {
        <T> PropertyMapper.Source<T> apply(PropertyMapper.Source<T> source);
    }

}

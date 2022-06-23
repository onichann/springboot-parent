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





    //1.正常方法 返回PropertyMapper 类型 ,调用alwaysApplying 返回
    public PropertyMapper alwaysApplyingWhenNonNull() {
//        return this.alwaysApplying(this::whenNonNull);
        return this.alwaysApplying(new SourceOperator() {
            @Override
            public <T> void apply(PropertyMapper.Source<T> source) {
                //方法的返回值， 没用，接口没返回值
                PropertyMapper.Source<T> tSource = whenNonNull(source);
            }
        });
    }


    //2.方法入参要接口类型
    public PropertyMapper alwaysApplying(SourceOperator operator) {
        Assert.notNull(operator, "Operator must not be null");
//        operator.apply();
        return null;
    }

    //这是接口
    @FunctionalInterface
    public interface SourceOperator {
        <T> void apply(PropertyMapper.Source<T> source);
    }

    //接口的入参给它了， 它返回一个类型 PropertyMapper.Source, 回到第一步，到了接口里的返回就是这个，但是接口不需要返回值，
    //如果接口需要返回值，它的返回值是接口返回值的子类

    //方法入参和接口一样，返回值是其子类，如果接口返回值为null，那就随便返回
    private <T> PropertyMapper.Source<T> whenNonNull(PropertyMapper.Source<T> source) {
        return source.whenNonNull();
    }

}

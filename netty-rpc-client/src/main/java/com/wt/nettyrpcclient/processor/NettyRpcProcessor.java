package com.wt.nettyrpcclient.processor;

import com.wt.nettyrpcclient.anno.RpcReference;
import com.wt.nettyrpcclient.proxy.RpcClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author wutong
 * @date 2022/4/25 23:19
 * 说明: bean后置增加
 */
@Component
public class NettyRpcProcessor implements BeanPostProcessor {

    @Autowired
    RpcClientProxy rpcClientProxy;

    /**
     * 自定义注解的注入
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //1.查看bean的字段中有没有注解
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            //2.查找字段中是否包含这个注解
            RpcReference annotation = field.getAnnotation(RpcReference.class);
            if (annotation != null) {
                //获取代理对象
                Object proxy = rpcClientProxy.getProxy(field.getType());
                //属性注入
                field.setAccessible(true);
                try {
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}

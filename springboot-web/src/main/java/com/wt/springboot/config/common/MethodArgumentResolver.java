package com.wt.springboot.config.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @date 2019-12-12 下午 6:07
 * PROJECT_NAME gtyzt-sand
 */
@Configuration
public class MethodArgumentResolver {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void addCustomArgumentResolvers(){
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new MultipartFilesMethodArgumentResolver());
        argumentResolvers.addAll(Objects.requireNonNull(requestMappingHandlerAdapter.getArgumentResolvers()));
        requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);
    }


}

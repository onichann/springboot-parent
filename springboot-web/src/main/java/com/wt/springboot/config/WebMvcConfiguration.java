package com.wt.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 正常实现WebMvcConfigurer接口即可，这样可以使用springmvc的配置项 application.properties
 * 或者extends WebMvcConfigurationSupport
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    //解决中文返回浏览器乱码
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(StringHttpMessageConverter.class::isInstance);
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    //url 到视图映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("/home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(xxInterceptor).addPathPatterns("/**");
    }

    //允许跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");

//        Access-Control-Allow-Origin:| * // 授权的源控制
//        Access-Control-Max-Age:// 授权的时间
//        Access-Control-Allow-Credentials: true | false // 控制是否开启与Ajax的Cookie提交方式
//        Access-Control-Allow-Methods:[,]* // 允许请求的HTTP Method
//        Access-Control-Allow-Headers:[,]* // 控制哪些header能发送真正的请求
    }

    //将参数String对应格式的转成Date类型
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 静态资源映射   WebMvcAutoConfiguration  ->WebMvcAutoConfigurationAdapter-> ResourceProperties
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/swagger3/**").addResourceLocations("classpath:/static/swagger3/");
    }
}

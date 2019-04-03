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
public class WebMvcConfig implements WebMvcConfigurer{


//    @Bean
//    @ConditionalOnClass({Jackson2ObjectMapperBuilder.class})
//    public ObjectMapper jacksonObjectMapper2(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        return objectMapper;
//    }

//    @Bean
//    @ConditionalOnBean(ObjectMapper.class)
//    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(ObjectMapper objectMapper) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        converter.setObjectMapper(objectMapper);
//        return converter;
//    }

    //解决中文返回浏览器乱码
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
//        while (iterator.hasNext()){
//            HttpMessageConverter<?> next = iterator.next();
//            if(next instanceof  StringHttpMessageConverter){
//                iterator.remove();
//            }
//        }
//        converters.removeIf(new Predicate<HttpMessageConverter<?>>() {
//            @Override
//            public boolean test(HttpMessageConverter<?> httpMessageConverter) {
//                return httpMessageConverter instanceof StringHttpMessageConverter ;
//            }
//        });
        converters.removeIf(StringHttpMessageConverter.class::isInstance);
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    //url 到视图映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("thymeleaf/login");
        registry.addViewController("/jsplogin").setViewName("jsp/jsplogin");
        registry.addViewController("/home").setViewName("thymeleaf/home");
        registry.addViewController("/").setViewName("thymeleaf/home");
        registry.addViewController("/hello").setViewName("thymeleaf/hello");
        registry.addRedirectViewController("/error","jsp/error.jsp");
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
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/swagger3/**").addResourceLocations("classpath:/static/swagger3/");
    }

    /**
     * 配置jsp 和thymeleaf共存
     * @param webMvcProperties
     * @return
     */
//    @Bean
//    public ViewResolver viewResolver(@Autowired WebMvcProperties webMvcProperties) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix(webMvcProperties.getView().getPrefix());
//        resolver.setSuffix(webMvcProperties.getView().getSuffix());
//        resolver.setViewClass(JstlView.class);
//        resolver.setViewNames("*");
//        resolver.setOrder(2);
//        return resolver;
//    }
//
//    @Bean("itemlateR")
//    public ITemplateResolver templateResolver(@Autowired ThymeleafProperties thymeleafProperties) {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setTemplateMode(thymeleafProperties.getMode());
//        templateResolver.setPrefix(thymeleafProperties.getPrefix());
//        templateResolver.setSuffix(thymeleafProperties.getSuffix());
//        templateResolver.setCharacterEncoding(thymeleafProperties.getEncoding().name());
//        templateResolver.setCacheable(thymeleafProperties.isCache());
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine(@Qualifier("itemlateR") ITemplateResolver iTemplateResolver) {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(iTemplateResolver);
//        // templateEngine
//        return templateEngine;
//    }
//
//    @Bean
//    public ThymeleafViewResolver viewResolverThymeLeaf(SpringTemplateEngine springTemplateEngine,
//                                                       @Autowired ThymeleafProperties thymeleafProperties) {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(springTemplateEngine);
//        viewResolver.setCharacterEncoding(thymeleafProperties.getEncoding().name());
//        viewResolver.setOrder(1);
//        viewResolver.setViewNames(new String[]{"thymeleaf/*"});
//        return viewResolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
}

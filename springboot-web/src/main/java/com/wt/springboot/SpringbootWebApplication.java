package com.wt.springboot;

import com.wt.springboot.common.SpringContextUtil;
import com.wt.springboot.mybatis.typeHandler.StringConvertTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;

//@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@SpringBootApplication
//定义mybatis扫描
@MapperScan(
		//只当扫描包
		basePackages="com.wt.springboot.mybatis.mapper",
		//指定SqlSessionFactory，如果sqlSessionTemplate被指定，则作废
		sqlSessionFactoryRef = "sqlSessionFactory",
//        sqlSessionTemplateRef = "sqlSessionTemplate",
		//markerInterface = Class.class, //通过继承某个接口限制扫描，不常用
		annotationClass = Repository.class
)
//允许获取代理对象 AopContext.currentProxy()
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringbootWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SpringbootWebApplication.class, args);
		SpringContextUtil.setCtx(app);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootWebApplication.class);
	}

	@Autowired
	private RestTemplateBuilder builder;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate= builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
				return true;
			}

			@Override
			public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

			}
		});
		return restTemplate;
	}

//    @Bean
//    public MybatisCharSetConvertInterceptor mybatisCharSetConvertInterceptor(){
//        return new MybatisCharSetConvertInterceptor();
//    }
//
//    @Bean
//    public MybatisResultSetHandlerInterceptor mybatisResultSetHandlerInterceptor() {
//        return new MybatisResultSetHandlerInterceptor();
//    }

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private DynamicDataSourceProperties properties;

	@PostConstruct
	public void initMybatis(){
		TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
//        Collection<TypeHandler<?>> typeHandlers = typeHandlerRegistry.getTypeHandlers();
//        List<TypeHandler<?>> collect = typeHandlers.stream().filter(StringTypeHandler.class::isInstance).collect(Collectors.toList());
//        typeHandlers.removeIf(StringTypeHandler.class::isInstance);
		StringTypeHandler stringTypeHandler = new StringConvertTypeHandler();
		typeHandlerRegistry.register(String.class, JdbcType.VARCHAR,stringTypeHandler);

	}

}

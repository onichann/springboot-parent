package com.wt.springboot;

import com.wt.springboot.config.PropConfig;
import com.wt.springboot.mybatis.typeHandler.StringConvertTypeHandler;
import okhttp3.OkHttpClient;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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
@EnableConfigurationProperties(PropConfig.class)
public class SpringbootWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootWebApplication.class);
	}

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@PostConstruct
	public void initMybatis(){
		TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
		StringTypeHandler stringTypeHandler = new StringConvertTypeHandler();
		typeHandlerRegistry.register(String.class, JdbcType.VARCHAR,stringTypeHandler);
	}

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
				.connectTimeout(Duration.ofSeconds(60)).writeTimeout(Duration.ofSeconds(60))
				.readTimeout(Duration.ofSeconds(60)).callTimeout(Duration.ofSeconds(60))
				.retryOnConnectionFailure(true)
				.build();
	}




}

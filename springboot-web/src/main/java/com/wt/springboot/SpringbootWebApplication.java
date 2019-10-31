package com.wt.springboot;

import com.wt.springboot.common.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

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

}

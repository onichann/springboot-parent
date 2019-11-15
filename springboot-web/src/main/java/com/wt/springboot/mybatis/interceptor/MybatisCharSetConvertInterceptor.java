package com.wt.springboot.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author Administrator
 * @date 2019-11-08 下午 3:19
 * PROJECT_NAME sand-demo
 */
@Intercepts(
       @Signature(
            type = Executor.class,method = "query",
               args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
       )
)
public class MybatisCharSetConvertInterceptor implements Interceptor {

    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object proceed = invocation.proceed();

        return proceed;
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

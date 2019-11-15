package com.wt.springboot.mybatis.interceptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.Charsets;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Administrator
 * @date 2019-11-09 上午 9:54
 * PROJECT_NAME sand-demo
 */
@Intercepts({
        @Signature(
                type= ResultSetHandler.class,
                method ="handleResultSets",
                args = {Statement.class}
        )
})
public class MybatisResultSetHandlerInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if(invocation!=null){ return invocation.proceed();}

        try {
            Object[] args = invocation.getArgs();
            Statement statement=(Statement)args[0];
            ResultSet resultSet = statement.getResultSet();
            if (resultSet == null) {
                return invocation.proceed();
            }
            List<Object> resultList = Lists.newArrayList();
            List<String> columnLabels = getColumnLabels(resultSet);
            Map<String, Object> map = null;
            while (resultSet.next()) {
                map = Maps.newHashMap();
                for (String cl : columnLabels) {
                    Object value = resultSet.getObject(cl);
                    map.put(cl, charSetConvert(value));
                }
                resultList.add(map);
            }
            return resultList;
        } catch (Exception e) {
            return invocation.proceed();
        }
    }

    private Object charSetConvert(Object value) {
        if (value == null) {
            return value;
        }
        if (String.class.isAssignableFrom(value.getClass())) {
            try {
                return new String(value.toString().getBytes(Charsets.ISO_8859_1), "GBK");
            } catch (UnsupportedEncodingException e) {
                return value;
            }
        } else {
            return value;
        }
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private List<String> getColumnLabels(ResultSet rs) throws Exception{
        List<String> labels= new ArrayList<String>();
        ResultSetMetaData rsmd=rs.getMetaData();
        for(int i=0;i<rsmd.getColumnCount();i++){
            labels.add(rsmd.getColumnLabel(i+1));
        }
        return labels;
    }
}

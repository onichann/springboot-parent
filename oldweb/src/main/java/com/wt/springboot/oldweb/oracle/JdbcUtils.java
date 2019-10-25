package com.wt.springboot.oldweb.oracle;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * JDBC操作的工具类
 * @author wutong
 *
 */
public class JdbcUtils {
	
public  static Connection getConnection() throws Exception{

		
		Properties properties= new Properties();
		InputStream in= JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		properties.load(in);
		String driver=properties.getProperty("driver");
		String jdbcUrl=properties.getProperty("jdbcUrl");
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
		return connection;
	}


/**
 * 释放Connection 对象
 * @param rs
 * @param statement
 * @param conn
 */
public static void release(ResultSet rs,Statement statement,Connection conn){
	
	try {
		if(rs!=null) rs.close();
		if(statement != null) statement.close();
		if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
   }

	public static void main(String[] args)  {
		Connection conn=null;
		PreparedStatement preparedstatement=null;
		ResultSet rs=null;
		try {
			conn= JdbcUtils.getConnection();
			System.out.println(conn);
			String sql="select * from t_gg_rwb";
			preparedstatement=conn.prepareStatement(sql);
			rs =preparedstatement.executeQuery();
			List<Map<String,Object>> values= new ArrayList<Map<String,Object>>();
			List<String> columnLabels=getColumnLabels(rs);
			Map<String,Object> map=null;
			 while(rs.next()){
				 	map=new HashMap<String,Object>();
				 	for(String columnLabel1:columnLabels){
				 		Object value=rs.getObject(columnLabel1);
				 		map.put(columnLabel1, value);
				 	}
				 	values.add(map);
			 }
			System.out.println(values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			release(rs,preparedstatement,conn);
		}
		
	}
	
	public static List<String> getColumnLabels(ResultSet rs) throws Exception{
		List<String> labels= new ArrayList<String>();
		ResultSetMetaData rsmd=rs.getMetaData();
		for(int i=0;i<rsmd.getColumnCount();i++){
			labels.add(rsmd.getColumnLabel(i+1));
		}
		return labels;
	}
}

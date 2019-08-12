//package com.wt.springboot.mybatis;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
//import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
//
///**
// * @author Administrator
// * @date 2019-04-02 上午 11:59
// * PROJECT_NAME sand-demo
// */
//public class OracleDialect implements IDialect {
//
//    public OracleDialect() {
//    }
//
//    @Override
//    public DialectModel buildPaginationSql(String originalSql, long offset, long limit) {
//        limit = offset >= 1L ? offset + limit : limit;
//        String sql = "SELECT * FROM( SELECT ROWNUM ROW_ID,TEMP.* FROM("+originalSql+")TEMP ) T WHERE  T.ROW_ID <= ? AND T.ROW_ID > ? ";
//        return (new DialectModel(sql, limit, offset)).setConsumerChain();
//    }
//}

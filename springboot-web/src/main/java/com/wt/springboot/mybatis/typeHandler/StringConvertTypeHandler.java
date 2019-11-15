package com.wt.springboot.mybatis.typeHandler;

import org.apache.commons.codec.Charsets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.StringTypeHandler;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 * @date 2019-11-09 下午 2:26
 * PROJECT_NAME sand-demo
 */
public class StringConvertTypeHandler extends StringTypeHandler {

    private static final Log logger = LogFactory.getLog(StringConvertTypeHandler.class);

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return charSetConvert(super.getNullableResult(rs, columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return charSetConvert(super.getNullableResult(rs, columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return charSetConvert(super.getNullableResult(cs, columnIndex));
    }

    private String charSetConvert(String value) {
        if (value == null) {
            return value;
        }
        try {
            return new String(value.getBytes(Charsets.ISO_8859_1), "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.debug("字符集转失败:"+e.getMessage());
            return value;
        }
    }
}

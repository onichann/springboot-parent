package com.wt.springboot.excel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Objects;

public class SimpleValidHandler extends ValidHandler {
    private static final Log logger = LogFactory.getLog(SimpleValidHandler.class);

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return ExcelColum.class;
    }

    @Override
    protected ValidResponse validValue(ValidRequest request) {
        Object cellValue = request.getCellBean().getCellValue();
        if (!StringUtils.isEmpty(cellValue)) {
            return checkType(request.getField(), request.getCellBean().getCellValue());
        }
        return new ValidResponse().setSuccess(true).setErrorMessage(null);
    }

    private ValidResponse checkType(Field field, Object value) {
        ExcelColum excelColum = field.getDeclaredAnnotation(ExcelColum.class);
        String typeName = field.getType().getName();
        String message = excelColum.message();
        boolean canConvert = false;
        String errorMessage = "";
        if (Objects.equals("int", typeName)) {
            try {
                //默认excel 数字类型、日期读取都是double类型，这个判断1==1.0情况,已改成String类型这个判断可以去除
                BigDecimal decimal = new BigDecimal(String.valueOf(value));
                BigDecimal bdValue = decimal.setScale(0, BigDecimal.ROUND_DOWN);
                int intValue = bdValue.intValue();
                if (decimal.doubleValue() == Double.valueOf(value.toString()) && intValue == Double.valueOf(value.toString())) {
                    canConvert = true;
                } else {
                    //如果数据范围超过则验证不通过，建议数值类型都用Double
                    NumberUtils.parseNumber(Objects.toString(value), Integer.class);
                    canConvert = true;
                }
            } catch (NumberFormatException e) {
                errorMessage = MessageFormat.format("{0}! 类型验证不通过,校验所需类型：{1}，校验传入值：{2}，具体信息：{3}", message, typeName, value, e.getMessage());
                logger.debug(errorMessage);
            }
        } else if (Objects.equals("java.lang.Double", typeName)) {
            try {
                NumberUtils.parseNumber(Objects.toString(value), Double.class);
                canConvert = true;
            } catch (Exception e) {
                errorMessage = MessageFormat.format("{0}! 类型验证不通过,校验所需类型：{1}，校验传入值：{2}，具体信息：{3}", message, typeName, value, e.getMessage());
                logger.debug(errorMessage);
            }
        } else if (Objects.equals("boolean", typeName)) {
            try {
                Boolean.parseBoolean(Objects.toString(value));
                canConvert=true;
            } catch (Exception e) {
                errorMessage = MessageFormat.format("{0}! 类型验证不通过,校验所需类型：{1}，校验传入值：{2}，具体信息：{3}", message, typeName, value, e.getMessage());
                logger.debug(errorMessage);
            }
        } else if (Objects.equals("float", typeName)) {
            try {
                NumberUtils.parseNumber(Objects.toString(value), Float.class);
                canConvert = true;
            } catch (Exception e) {
                errorMessage = MessageFormat.format("{0}! 类型验证不通过,校验所需类型：{1}，校验传入值：{2}，具体信息：{3}", message, typeName, value, e.getMessage());
                logger.debug(errorMessage);
            }
        } else if (Objects.equals("java.time.LocalDate", typeName)||Objects.equals("java.time.LocalDateTime",typeName)||Objects.equals("java.util.Date",typeName)) {
            try {
                //excel获取的日期格式只为java.util.Date
                Assert.isTrue(value instanceof java.util.Date, "");
                canConvert = true;
            } catch (Exception e) {
                errorMessage = MessageFormat.format("{0}! 类型验证不通过,校验所需类型：{1}，校验传入值：{2}，具体信息：{3}", message, typeName, value, e.getMessage());
                logger.debug(errorMessage);
            }
        }else if(Objects.equals("java.lang.String",typeName)){
            canConvert=true;
        }
        return new ValidResponse().setSuccess(canConvert).setErrorMessage(errorMessage);
    }
}

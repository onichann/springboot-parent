package com.wt.springboot.config.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @date 2019-12-20 下午 4:46
 * description
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {
    private static final Log logger = LogFactory.getLog(StringToDateConverter.class);

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source.trim(), "yyyy-MM-dd");
        }
        if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source.trim(), "yyyy-MM-dd HH:mm:ss");
        }
        throw new IllegalArgumentException("Invalid value '" + source + "'");
    }

    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        }
        catch (ParseException e) {
            logger.warn(MessageFormat.format("转换{}为日期(pattern={})错误！", dateStr, format));
        }
        return date;
    }
}

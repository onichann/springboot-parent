package com.wt.springboot.guava.cache;

import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;

/**
 * @author wutong
 * @date 2019/9/25 15:56
 * PROJECT_NAME sand-demo
 */
@Service
public class SealTokenCache extends AbstractLodingCache<String,String> implements ILocalCache<String,String> {

    @Autowired
    private  SealTokenService sealTokenService;

    @Override
    protected String fetchData(String key) throws Exception {
        return sealTokenService.getToken();
    }

    @Override
    public String get(String key) {
        String value=null;
        try {
            value = getValue(key);
            logger.info("获取到签章的token:"+value);
        } catch (ExecutionException e) {
            logger.error(MessageFormat.format("无法根据缓存key:[{0}]获取缓存值",key));
            Throwables.throwIfUnchecked(e);
        }
        return value;
    }
}

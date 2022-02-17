package com.wt.springboot.core.redis;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pat.Wu
 * @date 2022/2/16 16:59
 * @description
 * https://cloud.tencent.com/developer/article/1096793
 *https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95#22-%E6%96%87%E4%BB%B6%E6%96%B9%E5%BC%8F%E9%85%8D%E7%BD%AE
 */
@Component
public class RedisDistributedLock {

    @Autowired
    private RedissonClient redissonClient;


}

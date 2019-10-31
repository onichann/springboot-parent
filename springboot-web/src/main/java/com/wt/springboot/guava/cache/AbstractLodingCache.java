package com.wt.springboot.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wutong
 * @date 2019/9/25 15:34
 * PROJECT_NAME sand-demo
 */
public abstract class AbstractLodingCache<K, V> {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private int maximumSize = 1000;
    private int expireAfterWriteDuration = 3;
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    private volatile LoadingCache<K,V> cache=null;

    public LoadingCache<K, V> getCache() {
        if(cache == null){
            synchronized (this) {
                if(cache == null){
                    cache = CacheBuilder.newBuilder().maximumSize(maximumSize)
                            .expireAfterWrite(expireAfterWriteDuration, timeUnit)
                            .recordStats()
                            .build(new CacheLoader<K, V>() {
                                @Override
                                public V load(K key) throws Exception {
                                    return fetchData(key);
                                }
                            });
                    logger.debug(MessageFormat.format("本地缓存{0}初始化成功",this.getClass().getSimpleName()));
                }
            }
        }
        return cache;
    }
    /**
     * 根据key从数据库或其他数据源中获取一个value，并被自动保存到缓存中。
     * @param key
     * @return value,连同key一起被加载到缓存中的。
     */
    protected abstract V fetchData(K key) throws Exception;

    /**
     * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
     * @param key
     * @return Value
     * @throws ExecutionException
     */
    protected V getValue(K key) throws ExecutionException {
        return getCache().get(key);
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public int getExpireAfterWriteDuration() {
        return expireAfterWriteDuration;
    }

    public void setExpireAfterWriteDuration(int expireAfterWriteDuration) {
        this.expireAfterWriteDuration = expireAfterWriteDuration;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}

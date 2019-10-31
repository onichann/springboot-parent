package com.wt.springboot.guava.cache;

/**
 * @author wutong
 * @date 2019/9/25 15:33
 * PROJECT_NAME sand-demo
 */
public interface ILocalCache<K,V> {
    /**
     * 从缓存中获取数据
     * @param key
     * @return value
     */
    public V get(K key);
}

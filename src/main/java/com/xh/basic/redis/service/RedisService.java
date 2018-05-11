package com.xh.basic.redis.service;

import java.util.Set;

/**
 * @author szq
 * @Package com.xh.basic.redis.service
 * @Description: to do ...
 * @date 2018/4/2015:46
 */
public interface RedisService {
    /**
     * 新增缓存并设置有效期
     * @param key
     * @param value
     * @param seconds
     */
    void addRedis(String key,Object value,Integer seconds);

    /**
     * 新增缓存
     * @param key
     * @param value
     */
    void addRedis(String key,Object value);

    /**
     * 根据key删除缓存
     * @param key
     */
    void delRedis(String key);

    /**
     * 获取到缓存的值
     * @param key
     * @return
     */
    Object getRedis(String key);

    /**
     * 批量删除缓存
     * @param key
     */
    void batchDelRedis(String key);

    /**
     * 获取key集合
     * @param key
     * @return
     */
    Set<String> getKeys(String key);
}

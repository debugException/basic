package com.xh.basic.redis.service;

import java.util.Set;

/**
 * @author szq
 * @Package com.xh.basic.redis.service
 * @Description: to do ...
 * @date 2018/4/2015:46
 */
public interface RedisService {
    void addRedis(String key,Object value,Integer seconds);
    void addRedis(String key,Object value);
    void delRedis(String key);
    Object getRedis(String key);
    void batchDelRedis(String key);
    Set<String> getKeys(String key);
}

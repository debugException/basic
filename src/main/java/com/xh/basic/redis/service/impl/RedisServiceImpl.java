package com.xh.basic.redis.service.impl;

import com.xh.basic.redis.config.RedisFinalConfig;
import com.xh.basic.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author szq
 * @Package com.xh.basic.redis.service.impl
 * @Description: redis基本操作
 * @date 2018/4/2015:47
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addRedis(String key, Object value, Integer seconds) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key.trim(), value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void addRedis(String key, Object value) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key.trim(), value, RedisFinalConfig.VALIDITY_MINUTE_3, TimeUnit.SECONDS);
    }

    @Override
    public void delRedis(String key) {
        redisTemplate.delete(key.trim());
    }

    @Override
    public Object getRedis(String key) {
        return redisTemplate.boundValueOps(key.trim()).get();
    }

    @Override
    public void batchDelRedis(String key) {
        Set<String> keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }

    @Override
    public Set<String> getKeys(String key) {
        return redisTemplate.keys(key);
    }

}

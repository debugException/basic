package com.xh.basic.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author szq
 * @Package com.xh.basic.redis.annotation
 * @Description: redis缓存标签
 * @date 2018/4/2016:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MCache {
    MCacheType cacheType() default MCacheType.QUERY;
    String keyOrIdx();
    String cacheGroup() default  "";
    int cacheTime() default  180;

    public enum MCacheType{
        QUERY,NOT_QUERY;
    }
}

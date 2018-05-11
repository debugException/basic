package com.xh.basic.redis.aop;

import com.xh.basic.redis.annotation.MCache;
import com.xh.basic.redis.service.impl.RedisServiceImpl;
import com.xh.basic.redis.util.ReflectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author szq
 * @Package com.xh.basic.redis.aop
 * @Description: to do ...
 * @date 2018/4/2016:11
 */
@Aspect
@Order(3)
@Component
public class CacheAspect {

    @Autowired
    private RedisServiceImpl redisService;

    private static Pattern pattern = Pattern.compile("[0-9]*");

    @Pointcut("@annotation(com.xh.basic.redis.annotation.MCache)")
    public void cacheAspect(){}

    @Around(value = "cacheAspect()")
    public Object around(ProceedingJoinPoint point) throws  Throwable{
        //初始化
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        MCache mCache = methodSignature.getMethod().getAnnotation(MCache.class);
        //类名纬度值
        String className = point.getTarget().getClass().getSimpleName();
        //方法名纬度值
        String methodName = point.getSignature().getName();
        //对应缓存的key
        String keyOrIdx = mCache.keyOrIdx();
        //方法参数
        Object[] arguments = point.getArgs();
        if (mCache.cacheType() == MCache.MCacheType.QUERY){
            //缓存组纬度值
            String cacheGroup = mCache.cacheGroup();
            //缓存变量纬度
            String varValue = getKeyValue(arguments, keyOrIdx);
            //组成缓存key
            String cacheKey = className + "_" + methodName + "_" + cacheGroup + "_" + varValue;
            //获取缓存并返回
            Object obj = redisService.getRedis(cacheKey);
            if (obj != null){
                return obj;
            }
            //没有查询到缓存数据则执行方法，返回结果并写入缓存
            Object object = point.proceed();
            if (object != null){
                redisService.addRedis(cacheKey, object, mCache.cacheTime());
            }
            return object;
        }else{
            //非查询缓存，indexOrKey
            //该类所有的方法
            Method[] methods = point.getTarget().getClass().getDeclaredMethods();
            //对应的keyGroup
            String[] keyGroups = keyOrIdx.split(",");
            for (String keyGroup : keyGroups) {
                String group = keyGroup.split("\\.")[0];
                String key = keyGroup.split("\\.")[1];
                String keyValue = getKeyValue(arguments,key);
                //轮循方法获得对应的查询方法
                for (Method method : methods) {
                    MCache methodCache = method.getAnnotation(MCache.class);
                    if(methodCache != null && methodCache.cacheType() == MCache.MCacheType.QUERY && methodCache.cacheGroup().equals(group)) {
                        //如果为keyValue为-1，对应查询缓存全部清楚
                        String queryKey = className+"_" + method.getName() + "_" + methodCache.cacheGroup() + "_*";
                        if("-1".equals(queryKey)) {
                            redisService.batchDelRedis(queryKey.trim());
                            break;
                        }
                        if(!"".equals(queryKey)) {
                            Set<String> queryKeys = redisService.getKeys(queryKey);
                            boolean isbreak = false;
                            for (String queryValue : queryKeys) {
                                String[] values = queryValue.split("_");
                                String queryKeyValue = values[values.length-1];
                                if(queryKeyValue.indexOf(keyValue) > -1) {
                                    redisService.batchDelRedis(queryKey.trim());
                                    isbreak = true;
                                    break;
                                }
                            }
                            if(isbreak) {
                                break;
                            }
                        }
                    }
                }
            }
            Object object = point.proceed();
            return object;
        }
    }

    private String getKeyValue(Object[] arguments, String keyOrIdx){
        String key = "";
        String index = "-1";
        if (index.equals(keyOrIdx)){
            return index;
        }
        String[] keys = keyOrIdx.split(",");
        if (arguments != null && arguments.length>0){
            for(String k : keys){
                //判断是否有数字
                boolean isNumber = pattern.matcher(k).matches();
                //如果是数字，获取参数下标，不是数字的话获取第一个参数对象的对应属性值
                if (isNumber){
                    int keyIdx = Integer.valueOf(k);
                    if (arguments != null && keyIdx < arguments.length){
                        key += arguments[keyIdx].toString();
                    }
                }else{
                    if (arguments != null && arguments.length >0){
                        Object object = ReflectionUtils.getFieldValue(arguments[0], k);
                        if (object != null){
                            key += object.toString();
                        }
                    }
                }
            }
        }
        return key;
    }
}

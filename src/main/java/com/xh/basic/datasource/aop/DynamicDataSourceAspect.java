package com.xh.basic.datasource.aop;

import com.xh.basic.datasource.annotation.DataSourceTarget;
import com.xh.basic.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author szq
 * @Package com.xh.basic.aop
 * @Description: to do ...
 * @date 2018/4/209:38
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(com.xh.basic.datasource.annotation.DataSourceTarget)")
    public void beforeSwitchDataSource(JoinPoint joinpoint){
        //获取当前访问的class
        Class<?> className = joinpoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinpoint.getSignature().getName();
        //得到方法的参数类型
        Class[] argClass = ((MethodSignature)joinpoint.getSignature()).getParameterTypes();
        String dataSource = DynamicDataSourceContextHolder.DEFAULT_DATASOURCE;
        try{
            //得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            //判断是否存在@DataSource注解
            if (method.isAnnotationPresent(DataSourceTarget.class)){
                DataSourceTarget annotation = method.getAnnotation(DataSourceTarget.class);
                //取出注解中的数据源名
                dataSource = annotation.value();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //切换数据源
        DynamicDataSourceContextHolder.setDataSource(dataSource);
    }

    @After("@annotation(com.xh.basic.datasource.annotation.DataSourceTarget)")
    public void afterSwitchDataSource(JoinPoint point){
        DynamicDataSourceContextHolder.clearDataSource();
    }
}

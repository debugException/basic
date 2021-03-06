package com.xh.basic.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author szq
 * @Package com.xh.basic.config
 * @Description: to do ...
 * @date 2018/4/209:15
 */
public class DynamicDataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DATASOURCE = "primary";

    private static final ThreadLocal<String>  CONTEXT_HOLDER = new ThreadLocal<>();

    //设置数据源名
    public static void setDataSource(String dbType){
        log.info("切换到{}数据源", dbType);
        CONTEXT_HOLDER.set(dbType);
    }

    //获取数据源名
    public static String getDataSource(){
        String dataSource = CONTEXT_HOLDER.get();
        if (dataSource!=null && dataSource.length()>0){
            return dataSource;
        }else{
            return DEFAULT_DATASOURCE;
        }
    }

    //清楚数据源名
    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }
}

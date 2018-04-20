package com.xh.basic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xh.basic.datasource.DynamicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedHashMap;

/**
 * @author szq
 * @Package com.xh.basic.config
 * @Description: to do ...
 * @date 2018/4/2013:11
 */
@Configuration
@EnableTransactionManagement
public class DruidConfig {

    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    private Environment environment;

    @Bean("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DruidDataSource primaryDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DruidDataSource slaveDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public AbstractRoutingDataSource dataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource());
        LinkedHashMap<Object, Object> targetDatasources = new LinkedHashMap<>();
        targetDatasources.put("primary", primaryDataSource());
        targetDatasources.put("slave", slaveDataSource());
        dynamicDataSource.setTargetDataSources(targetDatasources);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }


}

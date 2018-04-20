package com.xh.basic.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author szq
 * @Package com.xh.basic.config
 * @Description: to do ...
 * @date 2018/4/209:28
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源为{}", DynamicDataSourceContextHolder.getDataSource());
        return DynamicDataSourceContextHolder.getDataSource();
    }

}

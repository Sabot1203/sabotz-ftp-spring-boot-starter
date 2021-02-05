package com.sabotz.config;

import com.sabotz.ftppool.FTPClientPool;
import com.sabotz.ftppool.FTPProperties;
import com.sabotz.ftppool.PoolProperties;


import com.sabotz.utils.FTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 自动装配类
 *
 * @author sabot
 * @date 2021/2/4
 */

@Configuration
@EnableConfigurationProperties({FTPProperties.class, PoolProperties.class})
public class FTPAutoConfiguration {

    @Autowired
    private PoolProperties poolProperties;

    @Autowired
    private FTPProperties ftpProperties;

    @Bean("FTPUtil")
    public FTPUtils getUtil(){
        return new FTPUtils(new FTPClientPool(poolProperties,ftpProperties));
    }


}

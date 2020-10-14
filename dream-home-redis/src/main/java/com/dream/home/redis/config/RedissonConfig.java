package com.dream.home.redis.config;

import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hhz
 * @date 2020-05-14 11:31:04
 */
@Configuration
public class RedissonConfig {

    @Resource
    private RedisConfigProperties redisConfigProperties;

    /**
     * 添加redisson的bean
     *
     * @return redisson
     */
    @Bean
    public Redisson redisson() {
        // redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
            clusterNodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[0]));
        // 设置密码
        clusterServersConfig.setPassword(redisConfigProperties.getPassword());
        return (Redisson) Redisson.create(config);
    }

}

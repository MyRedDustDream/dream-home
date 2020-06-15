package com.dream.home.common.redis.config;

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

//    @Bean
//    public RedissonClient redisson() throws IOException {
//        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
//        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
//        return Redisson.create(config);
//    }

    /**
     * 添加redisson的bean
     *
     * @return redisson
     */
    @Bean
    public Redisson redisson() {
        // redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        List<String> clusterNodes = new ArrayList<>();
//        String[] nodes = new String[0];
        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
            clusterNodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
//            clusterNodes.add(redisConfigProperties.getCluster().getNodes().get(i));
//            String s = redisConfigProperties.getCluster().getNodes().get(i);
//
//            nodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
        }
        Config config = new Config();
        String[] strings = clusterNodes.toArray(new String[clusterNodes.size()]);
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
                .addNodeAddress(strings);
        // 设置密码
        clusterServersConfig.setPassword(redisConfigProperties.getPassword());


//        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000) // cluster state scan interval in milliseconds
//                .addNodeAddress("127.0.0.1:7000", "127.0.0.1:7001")
//                .addNodeAddress("127.0.0.1:7002");
//
//        RedissonClient redisson = Redisson.create(config);

        return (Redisson) Redisson.create(config);
    }

}

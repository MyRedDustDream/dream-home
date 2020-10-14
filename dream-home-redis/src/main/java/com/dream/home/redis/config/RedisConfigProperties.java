package com.dream.home.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis配置文件实体类
 *
 * @author hhz
 * @date 2020-06-23 19:10:29
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {
    private String password;
    private Cluster cluster;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public static class Cluster {
        private List<String> nodes;

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

}
package com.dream.home.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Redis服务
 *
 * @author hhz
 * @date 2020-09-28 16:53:30
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DreamHomeNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamHomeNacosApplication.class, args);
    }

}

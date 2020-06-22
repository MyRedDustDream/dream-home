package com.dream.home.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author hhz
 * @date 2020-06-21 16:30:48
 * {@link com.dream.home.dubbo.provider.service.DemoService} provider demo
 */
@Slf4j
@EnableDubbo(scanBasePackages = "com.dream.home.dubbo.provider.service")
@PropertySource(value = "classpath:/provider-config.properties")
public class DemoServiceProviderBootstrap {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DemoServiceProviderBootstrap.class);
        context.refresh();
        log.info("DemoService provider is starting...");
        System.in.read();
    }
}
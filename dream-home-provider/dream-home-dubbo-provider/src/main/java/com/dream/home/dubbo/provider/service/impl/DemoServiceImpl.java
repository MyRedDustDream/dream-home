package com.dream.home.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.dream.home.dubbo.provider.service.DemoService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hhz
 * @date 2020-06-21 13:55:24
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {

    @Value("${demo.service.name}")
    private String serviceName;

    /**
     * 获得名称
     *
     * @param name 名称
     * @return 结果
     */
    @Override
    public String sayName(String name) {
        RpcContext rpcContext = RpcContext.getContext();
        return String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s",
                serviceName,
                rpcContext.getLocalPort(),
                rpcContext.getMethodName(),
                name,
                name);
    }

}

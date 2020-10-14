package com.dream.home.redis.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * Nacos控制器
 *
 * @author hhz
 * @date 2020-09-28 16:10:57
 */
@Slf4j
@RefreshScope
@Api("Nacos控制器")
@RestController
@RequestMapping("/config")
public class NacosController {

    @Value("${spring.nacos.config.server-addr}")
    private static String serverAddr;

    @Value("${spring.nacos.config.namespace}")
    private static String namespace;

    @Value("${spring.application.name}")
    private static String applicationName;

    @NacosInjected
    private ConfigService configService;

    //    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    @NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private boolean useLocalCache;

    @NacosValue("test")
    private String test;

    public static void main(String[] args) throws NacosException {
        final String remoteAddress = "localhost:8848";
//        final String remoteAddress = "47.74.227.13";
        final String groupId = "dream-home";
        final String dataId = "dream-home";
        final String rule = "[\n"
                + "  {\n"
                + "    \"resource\": \"TestResource\",\n"
                + "    \"count\": 5.0\n"
                + "  }\n"
                + "]";
        ConfigService configService = NacosFactory.createConfigService(remoteAddress);
        boolean flag = configService.publishConfig(dataId, groupId, rule);
        log.info("flag:{}", flag);
    }

    @RequestMapping("/getServerAddress")
    public String getServerAddress() {
        String serverAddress = applicationName + ":";
        log.info("serverAddress:{}", serverAddress);
        return serverAddress;
    }

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

    /**
     * curl -X POST 'http://localhost:8080/config?dataId=example&content=useLocalCache=false'
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> publish(@RequestParam String dataId,
                                          @RequestParam(defaultValue = "DEFAULT_GROUP") String group,
                                          @RequestParam String content) throws NacosException {
        boolean result = configService.publishConfig(dataId, group, content);
        if (result) {
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/getConfig")
    @ResponseBody
    public String getConfig(@RequestParam(value = "dataId") String dataId,
                            @RequestParam(value = "group", required = false, defaultValue = "DEFAULT_GROUP") String group) {
        String content = "";
        try {
            configService.getConfig(dataId, group, 3000L);
        } catch (NacosException e) {
            log.error("NacosException:{}", e.getMessage(), e);
        }
        log.info("content:{}", content);
        return content;
    }

    @GetMapping("/getCustomConfig")
    @ResponseBody
    public String getCustomConfig(@RequestParam(value = "serverAddr", required = false) String serverAddr,
                                  @RequestParam(value = "namespace", required = false) String namespace,
                                  @RequestParam(value = "dataId") String dataId,
                                  @RequestParam(value = "group", required = false, defaultValue = "DEFAULT_GROUP") String group) {
        String content = "";
        Properties properties = new Properties();
        // nacos服务器地址，127.0.0.1:8848
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 配置中心的命名空间id
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        ConfigService thisConfigService;
        try {
            thisConfigService = NacosFactory.createConfigService(properties);
            // 根据dataId、group定位到具体配置文件，获取其内容. 方法中的三个参数分别是: dataId, group, 超时时间
            content = thisConfigService.getConfig(dataId, group, 3000L);
        } catch (NacosException e) {
            log.error("NacosException:{}", e.getMessage(), e);
        }
        log.info("content:{}", content);
        return content;
    }

}

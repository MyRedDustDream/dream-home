package com.dream.home.redis.controller;

import com.dream.home.redis.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Redis控制器
 *
 * @author hhz
 * @date 2020-08-17 13:59:03
 */
@Api("Redis控制器")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedissonUtil redissonUtil;

    @ApiOperation("getLock")
    @GetMapping(value = "/getLock", name = "首页")
    public Boolean getLock(@RequestParam("key") String key) {
        return redissonUtil.getLock(key);
    }

}

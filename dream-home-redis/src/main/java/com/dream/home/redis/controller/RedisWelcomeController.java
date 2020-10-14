package com.dream.home.redis.controller;

import com.dream.home.common.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redis欢迎控制器
 *
 * @author hhz
 * @date 2020-05-14 09:45:23
 */
@Api("Redis欢迎控制器")
@Controller
public class RedisWelcomeController {

    @ApiOperation("Redis首页")
    @GetMapping(value = "/", name = "Redis首页")
    public String homePage() {
        return CommonConstant.FORWARD_KEY + CommonConstant.COLON_SEPARATOR + CommonConstant.HOME_PAGE;
    }

}

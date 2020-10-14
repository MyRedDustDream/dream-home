package com.dream.home.redis.controller;

import com.dream.home.common.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Nacos欢迎控制器
 *
 * @author hhz
 * @date 2020-09-28 16:10:42
 */
@Api("Nacos欢迎控制器")
@Controller
public class NacosWelcomeController {

    @ApiOperation("Nacos首页")
    @GetMapping(value = "/", name = "Nacos首页")
    public String homePage() {
        return CommonConstant.FORWARD_KEY + CommonConstant.COLON_SEPARATOR + CommonConstant.HOME_PAGE;
    }

}

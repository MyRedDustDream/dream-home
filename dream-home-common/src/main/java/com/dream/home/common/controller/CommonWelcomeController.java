package com.dream.home.common.controller;

import com.dream.home.common.constant.CommonConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Common欢迎控制器
 *
 * @author hhz
 * @date 2020-05-14 09:45:23
 */
@Controller
public class CommonWelcomeController {

    @GetMapping(value = "/", name = "首页")
    public String homePage() {
        return CommonConstant.FORWARD_KEY + CommonConstant.COLON_SEPARATOR + CommonConstant.HOME_PAGE;
    }

}

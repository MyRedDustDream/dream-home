package com.dream.home.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hhz
 * @date 2020-05-14 09:45:23
 */
@Controller
public class WelcomeController {

    @GetMapping(value = "/", name = "欢迎页")
    public String welcome() {
        return "forward:welcome.html";
    }

}

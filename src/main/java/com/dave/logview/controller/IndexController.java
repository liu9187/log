package com.dave.logview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * wwj
 * 2018/8/24  17:07
 */
@Controller
public class IndexController {

    /**
     * 主页
     */
    @RequestMapping("/")
    public String index(){
        return "logView";
    }
}

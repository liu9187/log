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
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/logView")
    public String logView(){
        return "logView";
    }
    @RequestMapping("/")
    public String logView1(){
        return "logView";
    }
}

package com.dave.logview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * wwj
 * 2018/8/24  17:07
 */
@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 主页
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/")
    public String logView(){
        logger.info("当前系统："+System.getProperty("os.name"));
        if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
            return "logView";
        }
        return "logViewWindows";
    }
}

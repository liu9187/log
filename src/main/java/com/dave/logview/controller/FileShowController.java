package com.dave.logview.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * wwj
 * 2018/8/27  16:40
 */
@Controller
@RequestMapping("/fileShow")
public class FileShowController {

    private static Logger logger = LoggerFactory.getLogger(FileShowController.class);

    /**
     * 根目录
     */
    @RequestMapping("/listRoot")
    @ResponseBody
    public Object listRoot() {
        logger.info("当前用户工作地址：" + System.getProperty("user.dir") + ",开始遍历!");
        String userDir = System.getProperty("user.dir");
        return listFile(userDir);
    }

    /**
     * 目录查看
     */
    @RequestMapping("/listDir")
    @ResponseBody
    public Object listDir(@RequestParam  String pathDir) {
        logger.info("目标路径：" + pathDir + ",开始遍历!");
        return listFile(pathDir);
    }

    private Object listFile(String filedir) {
        Map<String, Object> result = new HashMap<>();
        List<String> directorys = new ArrayList<>();
        List<String> files = new ArrayList<>();
        try {
            //开始遍历
            File file = new File(filedir);
            File[] fs = file.listFiles();
            for (File f : fs) {
                if(f.getName().startsWith(".")){
                    //隐藏文件 暂不显示
                    continue;
                }
                if (f.isDirectory()) {
                    directorys.add(f.getName());
                }
                if (f.isFile()) {
                    files.add(f.getName());
                }
            }
            logger.info("目标路径：" + filedir + ",遍历完成!");
        } catch (Exception e) {
            logger.error("遍历失败:" + e.getMessage(), e);
        }
        //路径不管是不是报错还是给他
        result.put("files", files);
        result.put("directorys", directorys);
        result.put("pwd", filedir);
        return result;
    }

}

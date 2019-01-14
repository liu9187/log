package com.dave.logview.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import com.dave.logview.common.CommonResult;
import com.dave.logview.config.LogViewConfig;
import com.dave.logview.websocket.RemoteWebSocketHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wwj
 * 2019/1/14  14:20
 * 远程日志文件列表查看
 */
@Controller
@RequestMapping("/remoteFileShow")
public class RemoteFileShowController {


    private static Logger logger = LoggerFactory.getLogger(RemoteWebSocketHandle.class);

    private Connection conn = null;

    private SFTPv3Client client = null;

    private final int port = 22;

    @Autowired
    private LogViewConfig logViewConfig;

    /**
     * 根目录
     */
    @RequestMapping("/listRoot")
    @ResponseBody
    public Object listRoot() {
        logger.info("当前远程服务器获取用户工作根目录默认/home!");
        String userDir = "/home";
        return listFile(userDir);
    }

    /**
     * 目录查看
     */
    @RequestMapping("/listDir")
    @ResponseBody
    public Object listDir(@RequestParam String pathDir) {
        logger.info("目标路径：" + pathDir + ",开始遍历!");
        return listFile(pathDir);
    }

    private CommonResult listFile(String filedir) {
        Map<String, Object> result = new HashMap<>();
        List<String> directorys = new ArrayList<>();
        List<String> files = new ArrayList<>();
        try {
            //服务器登录参数
            String hostname = logViewConfig.getRemoteIp();
            String username = logViewConfig.getRemoteUserName();
            String password = logViewConfig.getRemotePassWord();

            conn = new Connection(hostname, port);
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                logger.info("登录服务器失败!");
                return CommonResult.error("用户名或密码失败!");
            }
            //开始遍历
            client = new SFTPv3Client(conn);
            List<SFTPv3DirectoryEntry> ls = client.ls(filedir);
            for (SFTPv3DirectoryEntry sftPv3DirectoryEntry : ls) {
                if (".".equals(sftPv3DirectoryEntry.filename) || "..".equals(sftPv3DirectoryEntry.filename)) {
                    continue;
                }
                if (sftPv3DirectoryEntry.filename.startsWith(".")) {
                    //隐藏文件 不显示
                    continue;
                }
                if (sftPv3DirectoryEntry.attributes.isDirectory()) {
                    directorys.add(sftPv3DirectoryEntry.filename);
                }
                if (sftPv3DirectoryEntry.attributes.isRegularFile()) {
                    files.add(sftPv3DirectoryEntry.filename);
                }
            }
            logger.info("目标路径：" + filedir + ",遍历完成!");
        } catch (Exception e) {
            logger.error("遍历失败:" + e.getMessage(), e);
            return CommonResult.error(e.getMessage());
        } finally {
            //每次请求都打开一次 有点浪费
            if (client != null) {
                client.close();
            }
            if (conn != null) {
                conn.close();
            }
            logger.info("连接关闭!");
        }
        result.put("files", files);
        result.put("directorys", directorys);
        result.put("pwd", filedir);
        return CommonResult.success(result);
    }

}

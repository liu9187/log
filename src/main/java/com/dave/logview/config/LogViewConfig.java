package com.dave.logview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * wwj
 * 2019/1/11  13:32
 */
@Component
public class LogViewConfig {


    @Value("${logView.username}")
    private String username;

    @Value("${logView.password}")
    private String password;

    //200 服务器

    public static String remoteIp;


    public static String remoteUserName;


    public static String remotePassWord;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    @Value("${logView.remoteIp}")
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getRemoteUserName() {
        return remoteUserName;
    }

    @Value("${logView.remoteUserName}")
    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName;
    }

    public String getRemotePassWord() {
        return remotePassWord;
    }

    @Value("${logView.remotePassWord}")
    public void setRemotePassWord(String remotePassWord) {
        this.remotePassWord = remotePassWord;
    }
}

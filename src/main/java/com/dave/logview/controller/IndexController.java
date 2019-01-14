package com.dave.logview.controller;

import com.dave.logview.common.CommonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String index() {
        return "index";
    }

    @RequestMapping("/view")
    public String logView() {
        logger.info("当前系统：" + System.getProperty("os.name"));
        if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
            return "logView";
        }
        return "logViewWindows";
    }

    @GetMapping("/login")
    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            //登录过的
            return "index2";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@RequestParam String username, @RequestParam String password, @RequestParam String verity) {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
            currentUser.login(token);
            return CommonResult.success("登录成功");
        } catch (IncorrectCredentialsException e) {
            logger.error(e.getMessage(), e);
            return CommonResult.error("账号或密码不正确");
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            return CommonResult.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return CommonResult.error(e.getMessage());
        }

    }
}

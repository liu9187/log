package com.dave.logview.controller;

import com.dave.logview.common.CaptchaUtil;
import com.dave.logview.common.CommonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * wwj
 * 2018/8/24  17:07
 */
@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final String checkCode = "checkCode";

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

    @RequestMapping("/remoteView")
    public String remoteView() {
        return "remoteLogView";
    }


    @GetMapping("/login")
    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            //登录过的
            return "index";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@RequestParam String username, @RequestParam String password, @RequestParam String verity) {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            if (!verity.equalsIgnoreCase((String)session.getAttribute(checkCode))) {
                return CommonResult.error("验证码错误");
            }
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

    @RequestMapping("/createCode")
    @ResponseBody
    public void createCode(HttpServletResponse response) throws Exception {
        response.setHeader("Expires", "-1");//控制缓存的失效日期
        response.setHeader("Cache-Control", "no-cache");//必须先与服务器确认返回的响应是否被更改，然后才能使用该响应来满足后续对同一个网址的请求
        response.setHeader("Pragma", "-1");
        CaptchaUtil captchaUtil = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(checkCode, captchaUtil.getString());
        // 输出到web页面
        ImageIO.write(captchaUtil.getImage(), "jpg", response.getOutputStream());
    }

}

package com.dave.logview.shiro;

import com.dave.logview.config.LogViewConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * wwj
 * 2018/11/15  13:58
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {


    @Autowired
    private LogViewConfig logViewConfig;

    /**
     * 细化权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Map<String, String> user = (Map) principalCollection.getPrimaryPrincipal();
        Set<String> permsSet = new HashSet<>();
        //为空
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //查询用户信息
        Map result = new HashMap();
        String username = usernamePasswordToken.getUsername();

        if (StringUtils.isNotBlank(username) && logViewConfig.getUsername().equals(username)) {
            result.put("username", logViewConfig.getUsername());
            result.put("password", logViewConfig.getPassword());
        } else {
            //账号不存在
            throw new UnknownAccountException("账号或密码不正确");
        }

        //把user信息直接放到了信息当中去了
        return new SimpleAuthenticationInfo(result, logViewConfig.getPassword(), getName());
    }
}

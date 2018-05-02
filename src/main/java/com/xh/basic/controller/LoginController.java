package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import com.xh.basic.dao.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szq
 * @Package com.xh.basic.controller
 * @Description: 登陆
 * @date 2018/5/215:43
 */
@RestController
public class LoginController {

    private final UserMapper userMapper;

    @Autowired
    public LoginController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public Resp notLogin(){
        return new Resp().success("您尚未登陆！");
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public Resp notRole(){
        return new Resp().success("您没有权限！");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Resp logout(){
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return new Resp().success("成功注销！");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Resp login(String username, String password){
        //从SecurityUtils里边创建一个subject
        Subject subject = SecurityUtils.getSubject();
        //在认证提交前准备token令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //执行认证登陆
        subject.login(token);
        //根据权限指定返回数据
        String role = userMapper.getRole(username);
        if ("user".equals(role)){
            return new Resp().success("欢迎登陆");
        }
        if ("admin".equals(role)){
            return new Resp().success("欢迎来到管理员页面");
        }
        return new Resp().success("权限错误");
    }
}

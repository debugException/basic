package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import com.xh.basic.dao.UserMapper;
import com.xh.basic.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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

    @PostMapping("/login")
    public Resp login(@RequestParam(value = "username", required = false) String username,
                      @RequestParam(value = "password", required = false) String password){
        String realPassword = userMapper.getPassword(username);
        if(realPassword == null){
            return new Resp("401","用户名错误");
        }else if(!realPassword.equals(password)){
            return new Resp("401", "密码错误");
        }else{
            return new Resp().success(JWTUtil.createToken(username));
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public Resp unauthorized(@PathVariable String message) throws UnsupportedEncodingException{
        return new Resp("401", message);
    }

}

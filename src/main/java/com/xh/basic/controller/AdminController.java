package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import com.xh.basic.dao.UserMapper;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author szq
 * @Package com.xh.basic.controller
 * @Description: 管理员
 * @date 2018/5/215:42
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserMapper userMapper;

    @Autowired
    public AdminController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @GetMapping("/getUser")
    @RequiresRoles("admin")
    public Resp getUser(){
        List<String> list = userMapper.getUser();
        return new Resp().success(list);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Resp getMessage(){
        return new Resp().success("您拥有管理员权限，可以获得该接口的信息！");
    }

    /**
     * 封号操作
     */
    @PostMapping("/banUser")
    @RequiresRoles("admin")
    public Resp updateStatus(String username){
        userMapper.banUser(username);
        return new Resp().success("成功封号！");
    }


}

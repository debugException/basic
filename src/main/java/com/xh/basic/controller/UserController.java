package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szq
 * @Package com.xh.basic.controller
 * @Description: 普通登录用户
 * @date 2018/5/215:41
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Resp getMessage() {
        return new Resp().success("您拥有用户权限，可以获得该接口的信息！");
    }

}

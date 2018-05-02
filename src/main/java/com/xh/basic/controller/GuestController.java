package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szq
 * @Package com.xh.basic.controller
 * @Description: 游客
 * @date 2018/5/215:36
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public Resp login(){
        return new Resp().success("欢迎进入，您的身份是游客。");
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Resp submitLogin(){
        return new Resp().success("您用户获得该接口的信息的权限");
    }
}

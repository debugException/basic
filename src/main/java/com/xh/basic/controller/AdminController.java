package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author szq
 * @Package com.xh.basic.controller
 * @Description: 管理员
 * @date 2018/5/215:42
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public Resp getMessage(){
        return new Resp().success("您拥有管理员权限，可以获得该接口的信息！");
    }
}

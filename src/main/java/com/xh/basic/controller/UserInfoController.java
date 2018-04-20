package com.xh.basic.controller;

import com.xh.basic.bean.ResponseBean;
import com.xh.basic.model.UserInfo;
import com.xh.basic.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/hello")
    public ResponseBean hello(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setData("hello world");
        return responseBean;
    }

    @PostMapping("/selectById")
    public ResponseBean selectById(Integer id){
        ResponseBean responseBean =  new ResponseBean();
        responseBean.setData(userInfoService.selectById(id));
        return responseBean;
    }
}

package com.xh.basic.controller;

import com.xh.basic.bean.Resp;
import com.xh.basic.bean.ResponseBean;
import com.xh.basic.redis.annotation.MCache;
import com.xh.basic.redis.service.RedisService;
import com.xh.basic.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/hello")
    public ResponseBean hello(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setData("hello world");
        return responseBean;
    }

    @PostMapping("/selectById")
    @MCache(keyOrIdx = "0", cacheGroup = "userInfo")
    public ResponseBean selectById(Integer id){
        ResponseBean responseBean =  new ResponseBean();
        logger.info("查询的id:" + id);
        responseBean = responseBean.rtnSuccess(userInfoService.selectById(id));
        return responseBean;
    }

    @PostMapping("/selectByUserName")
    @MCache(keyOrIdx = "0", cacheGroup = "userInfo")
    public ResponseBean selectByUserName(String userName){
        ResponseBean responseBean =  new ResponseBean();
        responseBean = responseBean.rtnSuccess(userInfoService.selectByUserName(userName));
        return responseBean;
    }

    @GetMapping("/jsonRst")
    public Resp getJsonRst(){
        Resp resp = new Resp("0","返回json格式数据");
        return resp;
    }
}

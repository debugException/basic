package com.xh.basic.service.impl;

import com.xh.basic.aop.DS;
import com.xh.basic.dao.UserInfoMapper;
import com.xh.basic.model.UserInfo;
import com.xh.basic.service.UserInfoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
//    @DS("slave")
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }
}

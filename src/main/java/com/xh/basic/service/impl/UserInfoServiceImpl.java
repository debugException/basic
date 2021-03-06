package com.xh.basic.service.impl;


import com.xh.basic.datasource.annotation.DataSourceTarget;
import com.xh.basic.dao.UserInfoMapper;
import com.xh.basic.model.UserInfo;
import com.xh.basic.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    @DataSourceTarget("slave")
    @Transactional
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo selectByUserName(String userName) {
        return null;
    }
}

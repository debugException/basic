package com.xh.basic.dao;

import com.xh.basic.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper
public interface UserInfoMapper {

    UserInfo selectById(@Param("id") Integer id);
}

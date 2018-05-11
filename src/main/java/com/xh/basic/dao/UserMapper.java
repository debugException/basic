package com.xh.basic.dao;

import com.xh.basic.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String getRole(String username);

    String getPassword(String username);

    String getRolePermission(String username);

    String getPermission(String username);

    Integer checkUserBanStatus(String username);

    List<String> getUser();

    void banUser(String username);
}
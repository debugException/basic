package com.xh.basic.shiro;

import com.xh.basic.dao.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author szq
 * @Package com.xh.basic.shiro
 * @Description: 自定义realm类
 * @date 2018/5/214:50
 */
public class CustomRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    private UserMapper userMapper;

    @Autowired
    private void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    /**
     * 获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---权限认证---");
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userMapper.getRole(username);
        Set<String> set = new HashSet<>();
        //需要将role封装到Set作为info.setRoles()的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的
     *
     * @param authenticationToken 用户身份信息
     * @return 返回封装了用户信息的AuthenticationInfo实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("---身份认证方法---");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从数据库获取对应用户名密码的用户
        String password = userMapper.getPassword(token.getUsername());
        if (null == password){
            throw new AccountException("用户名不正确");
        }else if(!password.equals(new String((char[])token.getCredentials()))){
            throw new AccountException("密码不正确");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
    }
}

package com.xh.basic.shiro;

import com.xh.basic.dao.UserMapper;
import com.xh.basic.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author szq
 * @Package com.xh.basic.shiro
 * @Description: 自定义realm类
 * @date 2018/5/214:50
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    private final UserMapper userMapper;

    @Autowired
    public CustomRealm(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }

    /**
     * 获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---权限认证---");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userMapper.getRole(username);
        //每个角色拥有默认的权限
        String rolePermission = userMapper.getRolePermission(username);
        //每个用户可以设置新的权限
        String permission = userMapper.getPermission(username);
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        //需要将role,permission封装到Set作为info.setRoles(), info.setStringPermissions()的参数
        roleSet.add(role);
        permissionSet.add(rolePermission);
        permissionSet.add(permission);
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
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
        String token = (String)authenticationToken.getCredentials();
        //解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (null == username || !JWTUtil.verify(token, username)){
            throw new AuthenticationException("token认证失败");
        }
        String password = userMapper.getPassword(username);
        if (null == password){
            throw new AccountException("用户不存在");
        }
        int ban = userMapper.checkUserBanStatus(username);
        if (ban == 1){
            throw new AuthenticationException("该用户已被封号");
        }

        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }
}

package com.xh.basic.config;

import com.xh.basic.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author szq
 * @Package com.xh.basic.config
 * @Description: Shiro配置类
 * @date 2018/5/214:36
 */
@Configuration
public class ShiroConfig {

    private Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
    /**
     * 过滤器默认权限表 {anon=anon, authc=authc, authcBasic=authcBasic, logout=logout,
     * noSessionCreation=noSessionCreation, perms=perms, port=port,
     * rest=rest, roles=roles, ssl=ssl, user=user}
     * <p>
     * anon, authc, authcBasic, user 是第一组认证过滤器
     * perms, port, rest, roles, ssl 是第二组授权过滤器
     * <p>
     * user 和 authc 的不同：当应用开启了rememberMe时, 用户下次访问时可以是一个user, 但绝不会是authc,
     * 因为authc是需要重新认证的, user表示用户不一定已通过认证, 只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     * 以前的一个用户登录时开启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
     *
     * @param securityManager 初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //setLoginUrl 默认会自动寻找web工程根目录下的“/login.jsp”页面或“/login”映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        //设置无权限时跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        //设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限“user”
        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限“admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //开发登录接口
        filterChainDefinitionMap.put("/login", "anon");
        //其余接口一律拦截
        //这行代码必须放在所有权限设置的最后，不然会导致所有url都被拦截
        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        logger.info("Shiro拦截器工厂类注入成功！");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入securityManager
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm
     * 必须写这个类，并加上@Bean注解，目的是注入CustomRealm，否则会影响CustomRealm类中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm(){
        return new CustomRealm();
    }
}

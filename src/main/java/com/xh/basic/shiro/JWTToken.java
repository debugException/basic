package com.xh.basic.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author szq
 * @Package com.xh.basic.shiro
 * @Description: to do ...
 * @date 2018/5/217:51
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

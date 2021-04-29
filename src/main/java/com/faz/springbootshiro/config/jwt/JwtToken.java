package com.faz.springbootshiro.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author  PZJ
 * @create  2021/4/29 16:15
 * @email   wuchzh0@gmail.com
 * @desc    JwtToken
 **/
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String token;

    public JwtToken(String token) {
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

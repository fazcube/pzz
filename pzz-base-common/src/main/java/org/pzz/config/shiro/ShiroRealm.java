package org.pzz.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.pzz.common.api.CommonAPI;
import org.pzz.common.vo.SysPermission;
import org.pzz.common.vo.SysRole;
import org.pzz.common.vo.SysUser;
import org.pzz.config.jwt.JwtToken;
import org.pzz.config.jwt.JwtUtil;
import org.pzz.config.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  PZJ
 * @create  2021/4/22 23:05
 * @email   wuchzh0@gmail.com
 * @desc    自定义realm
 **/
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private RedisUtil redisUtil;

    @Lazy
    @Resource
    private CommonAPI commonAPI;

    /**
     * 必须重写supports方法 限定realm只处理JwtToken
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("=======================开始授权=================================");
//        SysUser primaryPrincipal = (SysUser) principalCollection.getPrimaryPrincipal();
//        //根据primaryPrincipal查询用户的角色
//        List<SysRole> list = sysUserRoleService.findRoleByUsername(primaryPrincipal.getUsername());
//        if(!CollectionUtils.isEmpty(list)){
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            for (SysRole sysRole : list) {
//                simpleAuthorizationInfo.addRole(sysRole.getName());
//                List<SysPermission> permissionByRoleId = sysRolePermissionService.findPermissionByRoleId(sysRole.getId());
//                if(!CollectionUtils.isEmpty(permissionByRoleId)){
//                    permissionByRoleId.forEach(sysPermission -> {
//                        simpleAuthorizationInfo.addStringPermission(sysPermission.getName());
//                    });
//                }
//            }
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    /**
     * 认证
     * @param authenticationToken 已经改成了自己的token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //log.info("=======================开始认证=================================");
        //拿到token
        String token = (String) authenticationToken.getCredentials();
//        String username = JwtUtil.getUsername(token);
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(token == null){
            throw new AuthenticationException("token为空！");
        }
        SysUser user = checkUserTokenIsEffect(token);
        if(user!=null){
            log.info("======================="+user.getUsername()+"认证通过=================================");
        }
        return new SimpleAuthenticationInfo(user,token,getName());
    }

    /**
     * 校验token 和 用户的有效性
     * @param token
     * @return
     */
    public SysUser checkUserTokenIsEffect(String token){
        // 从token里面取到username
        String username = JwtUtil.getUsername(token);
        if(username == null){
            throw new AuthenticationException("token非法！");
        }
        // 根据用户名查询有无对应用户
        SysUser user = commonAPI.getUserByUsername(username);
        System.out.println("commonAPI查到的用户："+user);
        //SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }
        //校验用户状态 0正常 1冻结

        // 校验token是否超时失效  & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, user.getPassword())) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }
        return user;
    }

    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     *       用户过期时间 = Jwt有效时间 * 2。
     *
     * @param username
     * @param password
     * @return
     */
    public boolean jwtTokenRefresh(String token, String username, String password) {
        String cacheToken = String.valueOf(redisUtil.get(JwtUtil.PREFIX_USER_TOKEN + token));
        //如果redis中有相应的token 那么进入if
        if (cacheToken!=null && !cacheToken.equals("") && !cacheToken.equals("null")) {
            // 校验token有效性 如果redis中存储的value还是有效的 那么直接返回true 否则重新生成
            if (!JwtUtil.verify(cacheToken, username, password)) {
                String newToken = JwtUtil.sign(username, password);
                // 设置超时时间
                redisUtil.set(JwtUtil.PREFIX_USER_TOKEN + token, newToken);
                redisUtil.expire(JwtUtil.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
                log.info("——————————用户在线操作，更新token保证不掉线—————————JwtTokenRefresh——————— "+ token);
            }
            return true;
        }
        return false;
    }

}
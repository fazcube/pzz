package com.faz.springbootshiro.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.faz.springbootshiro.config.jwt.JwtToken;
import com.faz.springbootshiro.entity.SysPermission;
import com.faz.springbootshiro.entity.SysRole;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.service.SysRolePermissionService;
import com.faz.springbootshiro.service.SysUserRoleService;
import com.faz.springbootshiro.service.SysUserService;
import com.faz.springbootshiro.utils.MyByteSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author  PZJ
 * @create  2021/4/22 23:05
 * @email   wuchzh0@gmail.com
 * @desc    自定义realm
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 必须重写supports方法
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
        SysUser primaryPrincipal = (SysUser) principalCollection.getPrimaryPrincipal();
        //根据primaryPrincipal查询用户的角色
        List<SysRole> list = sysUserRoleService.findRoleByUsername(primaryPrincipal.getUsername());
        if(!CollectionUtils.isEmpty(list)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            for (SysRole sysRole : list) {
                simpleAuthorizationInfo.addRole(sysRole.getName());
                List<SysPermission> permissionByRoleId = sysRolePermissionService.findPermissionByRoleId(sysRole.getId());
                if(!CollectionUtils.isEmpty(permissionByRoleId)){
                    permissionByRoleId.forEach(sysPermission -> {
                        simpleAuthorizationInfo.addStringPermission(sysPermission.getName());
                    });
                }
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //根据用户名查询数据库 有无对应数据
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if(user!=null){
            log.info("--------------shiro认证用户名正确-----------------");
            return new SimpleAuthenticationInfo(user ,user.getPassword(),new MyByteSource(user.getSalt()),getName());
        }
        return null;
    }
}

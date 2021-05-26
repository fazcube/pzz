package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.pzz.common.api.CommonAPI;
import org.pzz.common.api.SysBaseAPI;
import org.pzz.common.vo.CommonSysUser;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.entity.SysUserRole;
import org.pzz.modules.mapper.SysRolePermissionMapper;
import org.pzz.modules.mapper.SysUserMapper;
import org.pzz.modules.mapper.SysUserRoleMapper;
import org.pzz.modules.service.SysRolePermissionService;
import org.pzz.modules.service.SysUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  PZJ
 * @create  2021/5/24 23:57
 * @email   wuchzh0@gmail.com
 * @desc    local调用方法（common模块通用）
 **/
@Service
public class SysBaseAPIImpl implements SysBaseAPI {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 1、根据用户名获取用户信息
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public CommonSysUser getUserByUsername(String username) {
        //接口返回的是CommmonUser对象 所以这里new一个准备返回
        CommonSysUser commonSysUser = new CommonSysUser();
        //根据拿到的参数查询出对应的SysUser对象
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
        //使用工具类copy对象
        BeanUtils.copyProperties(sysUser,commonSysUser);
        return commonSysUser;
    }

    /**
     * 2、根据用户名获取用户的角色名称集合
     * @param username 用户名
     * @return 名称集合
     */
    @Override
    public Set<String> getRoleSetByUsername(String username) {
        Set<String> roleSet = sysUserRoleMapper.getRoleSetByUsername(username);
        System.out.println("查询到的用户角色名称有："+roleSet);
        return roleSet;
    }

    /**
     * 3、根据用户名查询到用户的权限Set集合
     * @param username
     * @return
     */
    @Override
    public Set<String> getPermissionSetByUsername(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> permissionSetByUsername = sysRolePermissionMapper.getPermissionSetByUsername(username);
        for (SysPermission sysPermission : permissionSetByUsername) {
            permissionSet.add(sysPermission.getName());
        }
        System.out.println("查询到的用户权限名称有："+permissionSet);
        return permissionSet;
    }
}

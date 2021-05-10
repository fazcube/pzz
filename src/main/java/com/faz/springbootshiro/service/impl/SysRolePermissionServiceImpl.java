package com.faz.springbootshiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.faz.springbootshiro.mapper.SysPermissionMapper;
import com.faz.springbootshiro.mapper.SysRolePermissionMapper;
import com.faz.springbootshiro.entity.SysPermission;
import com.faz.springbootshiro.entity.SysRolePermission;
import com.faz.springbootshiro.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Resource
    private SysRolePermissionMapper sysRolePermissionDao;
    @Resource
    private SysPermissionMapper sysPermissionDao;

    /**
     * 根据角色id查询角色的权限集合
     * @param roleId 角色id
     * @return 权限集合
     */
    @Override
    public List<SysPermission> findPermissionByRoleId(String roleId) {
        List<SysPermission> list = new ArrayList<>();
        List<SysRolePermission> sysRolePermissions = sysRolePermissionDao.selectList(new LambdaQueryWrapper<SysRolePermission>()
                .eq(SysRolePermission::getRoleId, roleId));
        if(CollectionUtils.isEmpty(sysRolePermissions)){
            return list;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            list.add(sysPermissionDao.selectById(sysRolePermission.getPermissionId()));
        }
        return list;
    }
}

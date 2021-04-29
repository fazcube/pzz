package com.faz.springbootshiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.faz.springbootshiro.entity.SysPermission;
import com.faz.springbootshiro.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据角色id查询相应的权限集合
     * @return
     */
    List<SysPermission> findPermissionByRoleId(String roleId);
}

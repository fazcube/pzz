package com.faz.springbootshiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.faz.springbootshiro.entity.SysRole;
import com.faz.springbootshiro.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据用户名查询出角色列表
     * @param username
     * @return
     */
    List<SysRole> findRoleByUsername(String username);

}

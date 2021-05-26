package org.pzz.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据用户名查询到用户的权限集合
     * @param username
     * @return
     */
    List<SysPermission> getPermissionSetByUsername(String username);
}

package org.pzz.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据角色id查询相应的权限集合
     * @return
     */
    List<SysPermission> findPermissionByRoleId(String roleId);
}

package org.pzz.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pzz.modules.entity.SysRole;
import org.pzz.modules.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据用户名查询出角色列表
     * @param username
     * @return
     */
    List<SysRole> findRoleByUsername(String username);

}

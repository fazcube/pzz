package org.pzz.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 根据用户名查询到用户的权限集合
     * @param username
     * @return
     */
    List<SysPermission> getPermissionSetByUsername(@Param("username") String username);
}

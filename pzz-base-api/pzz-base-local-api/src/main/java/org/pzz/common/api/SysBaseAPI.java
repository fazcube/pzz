package org.pzz.common.api;

import org.pzz.common.vo.CommonSysUser;

import java.util.Set;

public interface SysBaseAPI extends CommonAPI{

    /**
     * 1、根据用户名获取用户信息
     * @param username
     * @return
     */
    CommonSysUser getUserByUsername(String username);

    /**
     * 2、根据用户名查询到用户的角色Set集合
     * @param username
     * @return
     */
    Set<String> getRoleSetByUsername(String username);

    /**
     * 3、根据用户名查询到用户的权限Set集合
     * @param username
     * @return
     */
    Set<String> getPermissionSetByUsername(String username);
}

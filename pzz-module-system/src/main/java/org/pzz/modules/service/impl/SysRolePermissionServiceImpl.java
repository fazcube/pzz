package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.mapper.SysPermissionMapper;
import org.pzz.modules.mapper.SysRolePermissionMapper;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.entity.SysRolePermission;
import org.pzz.modules.service.SysRolePermissionService;
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
     * 根据用户名查询到用户的权限集合
     * @param username
     * @return
     */
    @Override
    public List<SysPermission> getPermissionSetByUsername(String username) {
        sysRolePermissionDao.getPermissionSetByUsername(username);
        return null;
    }
}

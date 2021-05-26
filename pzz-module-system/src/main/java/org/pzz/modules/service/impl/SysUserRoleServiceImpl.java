package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.mapper.SysRoleMapper;
import org.pzz.modules.mapper.SysUserMapper;
import org.pzz.modules.mapper.SysUserRoleMapper;
import org.pzz.modules.entity.SysRole;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.entity.SysUserRole;
import org.pzz.modules.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserMapper sysUserDao;
    @Resource
    private SysUserRoleMapper sysUserRoleDao;
    @Resource
    private SysRoleMapper sysRoleDao;


}

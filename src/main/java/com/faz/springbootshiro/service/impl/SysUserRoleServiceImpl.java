package com.faz.springbootshiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.faz.springbootshiro.dao.SysRoleDao;
import com.faz.springbootshiro.dao.SysUserDao;
import com.faz.springbootshiro.dao.SysUserRoleDao;
import com.faz.springbootshiro.entity.SysRole;
import com.faz.springbootshiro.entity.SysUser;
import com.faz.springbootshiro.entity.SysUserRole;
import com.faz.springbootshiro.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao,SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private SysRoleDao sysRoleDao;

    /**
     * 根据用户名获取角色名
     * @param username
     * @return
     */
    @Override
    public List<SysRole> findRoleByUsername(String username) {
        List<SysRole> list = new ArrayList<>();
        //先根据用户名查询到用户id
        SysUser sysUser = sysUserDao.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
        //查询到用户id之后 在 用户角色表中 根据用户id查询到用户角色
        if(sysUser != null){
            List<SysUserRole> sysUserRoles = sysUserRoleDao.selectList(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId,sysUser.getId()));
            if(!CollectionUtils.isEmpty(sysUserRoles)){
                for (SysUserRole sysUserRole : sysUserRoles) {
                    list.add(sysRoleDao.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getId, sysUserRole.getRoleId())));
                }
            }
        }
        return list;
    }

}

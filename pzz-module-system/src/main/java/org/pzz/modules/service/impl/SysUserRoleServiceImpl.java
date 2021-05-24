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

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserMapper sysUserDao;
    @Resource
    private SysUserRoleMapper sysUserRoleDao;
    @Resource
    private SysRoleMapper sysRoleDao;

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

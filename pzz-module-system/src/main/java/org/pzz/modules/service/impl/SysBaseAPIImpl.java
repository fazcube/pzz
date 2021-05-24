package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.pzz.common.api.SysBaseAPI;
import org.pzz.common.vo.CommonSysUser;
import org.pzz.modules.entity.SysUser;
import org.pzz.modules.mapper.SysUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author  PZJ
 * @create  2021/5/24 23:57
 * @email   wuchzh0@gmail.com
 * @desc    local调用方法（common模块通用）
 **/
@Service
public class SysBaseAPIImpl implements SysBaseAPI {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public CommonSysUser getUserByUsername(String username) {
        CommonSysUser commonSysUser = new CommonSysUser();
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
        BeanUtils.copyProperties(sysUser,commonSysUser);
        return commonSysUser;
    }
}

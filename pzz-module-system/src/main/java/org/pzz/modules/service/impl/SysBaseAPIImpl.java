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
        //接口返回的是CommmonUser对象 所以这里new一个准备返回
        CommonSysUser commonSysUser = new CommonSysUser();
        //根据拿到的参数查询出对应的SysUser对象
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
        //使用工具类copy对象
        BeanUtils.copyProperties(sysUser,commonSysUser);
        return commonSysUser;
    }
}

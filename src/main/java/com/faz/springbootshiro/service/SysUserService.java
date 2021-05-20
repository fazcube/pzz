package com.faz.springbootshiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.faz.springbootshiro.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    boolean register(SysUser sysUser);

    SysUser randomOne();

    boolean updatePassword(SysUser sysUser,String newPassword);
}

package org.pzz.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pzz.modules.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    boolean register(SysUser sysUser);

    boolean updatePassword(SysUser sysUser,String newPassword);
}

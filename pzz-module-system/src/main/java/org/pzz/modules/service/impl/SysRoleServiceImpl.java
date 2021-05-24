package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.mapper.SysRoleMapper;
import org.pzz.modules.entity.SysRole;
import org.pzz.modules.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}

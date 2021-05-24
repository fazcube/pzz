package org.pzz.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pzz.modules.mapper.SysPermissionMapper;
import org.pzz.modules.entity.SysPermission;
import org.pzz.modules.service.SysPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
}

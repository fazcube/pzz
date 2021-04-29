package com.faz.springbootshiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.faz.springbootshiro.dao.SysPermissionDao;
import com.faz.springbootshiro.entity.SysPermission;
import com.faz.springbootshiro.service.SysPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {
}

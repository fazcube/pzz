package org.pzz.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.pzz.modules.entity.SysUserRole;

import java.util.Set;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Set<String> getRoleSetByUsername(@Param("username") String username);
}

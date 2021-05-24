package org.pzz.common.api;

import org.pzz.common.vo.CommonSysUser;

public interface SysBaseAPI extends CommonAPI{

    CommonSysUser getUserByUsername(String username);
}

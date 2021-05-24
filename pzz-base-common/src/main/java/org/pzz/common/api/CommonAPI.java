package org.pzz.common.api;

import org.pzz.common.vo.SysUser;

public interface CommonAPI {
    SysUser getUserByUsername(String username);
}

package org.pzz.common.system.api.fallback;

import org.pzz.common.system.api.SysBaseAPI;
import org.pzz.common.vo.SysUser;

public class SysBaseAPIFallback implements SysBaseAPI {

    @Override
    public SysUser getUserByUsername(String username) {
        return null;
    }
}

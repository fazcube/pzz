package org.pzz.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonSysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String roleId;
}

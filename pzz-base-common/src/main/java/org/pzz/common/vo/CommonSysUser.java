package org.pzz.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonSysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String password;
    private String salt;
}

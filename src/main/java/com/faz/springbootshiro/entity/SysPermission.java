package com.faz.springbootshiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.UUID)
    private String id;
    private String name;
    private String url;
}

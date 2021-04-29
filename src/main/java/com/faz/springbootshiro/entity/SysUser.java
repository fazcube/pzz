package com.faz.springbootshiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.UUID)
    private String id;
    private String username;
    private String password;
    private String salt;
}

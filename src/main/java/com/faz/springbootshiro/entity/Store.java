package com.faz.springbootshiro.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author  Fazcube
 * @create  2021-05-10
 * @email   wuchzh0@gmail.com
 * @desc    实体类
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    /**创建人*/
    private String createBy;

    /**创建日期*/
    private LocalDateTime createTime;

    /**更新人*/
    private String updateBy;

    /**更新日期*/
    private LocalDateTime updateTime;

    /**所属部门*/
    private String sysOrgCode;

    /**门店名称*/
    private String storeName;

    /**门店地址*/
    private String storeLocation;

    /**负责人*/
    private String personCharge;

    /**所属区域*/
    private String address;

    /**销量*/
    private String count;

    /**销售额*/
    private Double price;

    /**纬度*/
    private String latitude;

    /**经度*/
    private String longitude;

    /**负责人手机号*/
    private String personPhone;


}
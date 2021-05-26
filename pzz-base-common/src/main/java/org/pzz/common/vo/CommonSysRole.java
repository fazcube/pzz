package org.pzz.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonSysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String detail;

}

package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
public class UserRegisterReqDTO {


    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String mail;
    private Long deletionTime;
    private Date createTime;
    private Date updateTime;
    private Integer delFlag;

}

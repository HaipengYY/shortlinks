package com.nageoffer.shortlink.project.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * 短链接请求对象
 */
@Data
public class ShortLinkCreateReqDTO {


    private String domain;



    private String originUrl;




    private String gid;


    private Integer createdType;


    private Integer validDateType;


    private Date validDate;

    private String description;
}

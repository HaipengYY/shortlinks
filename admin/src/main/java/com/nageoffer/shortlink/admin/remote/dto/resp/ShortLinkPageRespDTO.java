package com.nageoffer.shortlink.admin.remote.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;



/**
 * 返回参数
 */
@Data
public class ShortLinkPageRespDTO {
    private Long id;


    private String domain;


    private String shortUri;


    private String fullShortUrl;


    private String originUrl;



    private String gid = "default";


    private Integer createdType;


    private Integer validDateType;

    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss", timezone = "GMT+8")
    private Date validDate;

    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String description;

}

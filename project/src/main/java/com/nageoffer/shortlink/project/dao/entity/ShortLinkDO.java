package com.nageoffer.shortlink.project.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.nageoffer.shortlink.project.common.database.BaseDo;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 短链接实体
 */
@Data
@TableName("t_link")
@Builder
public class ShortLinkDO extends BaseDo {

    private Long id;


    private String domain;


    private String shortUri;


    private String fullShortUrl;


    private String originUrl;


    private Integer clickNum = 0;


    private String gid = "default";


    private Integer enableStatus;


    private Integer createdType;


    private Integer validDateType;


    private Date validDate;

    private String description;

    /**
     * 网站标识
     */

    private String favicon;


}

package com.nageoffer.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 短链接分组实体
 */
@Data
@TableName("t_group")
public class GroupDO {
    private Long id;
    private String gid;
    private String username;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Integer delFlag;

}

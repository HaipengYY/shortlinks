package com.nageoffer.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nageoffer.shortlink.admin.common.database.BaseDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 短链接分组实体
 */
@Data
@TableName("t_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDO extends BaseDo {
    private Long id;
    private String gid;
    private String username;
    private String name;

    @TableField("sort_order")
    private int sort_order; // 确保映射正确
}

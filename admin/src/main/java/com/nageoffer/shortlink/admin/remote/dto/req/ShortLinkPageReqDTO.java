package com.nageoffer.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;


@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private String gid;
}

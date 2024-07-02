package com.nageoffer.shortlink.admin.dto.resp;


import lombok.Data;

@Data
public class ShortLinkGroupRespDTO {
    private String gid;
    private String username;
    private String name;
    private int sort_order;
}

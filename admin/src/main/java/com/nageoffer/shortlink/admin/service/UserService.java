package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
   UserRespDTO getUserByUserName(String username);

   /**
    * Check username is existed or not
    * @param username
    * @return Existed: Ture, not yet existed: False
    */
   Boolean hasUsername(String username);
   void register(UserRegisterReqDTO requestParam);


}

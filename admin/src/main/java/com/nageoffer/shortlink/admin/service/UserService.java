package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
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

   /**
    * 注册用户
    * @param requestParam 注册用户请求参数
    */
   void register(UserRegisterReqDTO requestParam);

   /**
    * 根据用户名修改用户
    */
   void update(UserUpdateReqDTO requestParam);

   UserLoginRespDTO login(UserLoginReqDTO requestParam);

   /**
    * 检查用户是否登录
    * @param token
    * @return
    */
   Boolean checkLogin(String username, String token);

   /**
    * 退出登录
    * @param username
    * @param token
    * @return
    */
   Void logout(String username, String token);
}

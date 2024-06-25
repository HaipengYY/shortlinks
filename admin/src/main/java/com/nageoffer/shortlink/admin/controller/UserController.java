package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.RealUserRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Get a user by their username
     */
    @GetMapping(value = "/api/shortlink/v1/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {

        return Results.success(userService.getUserByUserName(username));
    }
    /**
     * Get real user information by their username
     */
    @GetMapping(value = "/api/shortlink/v1/actual/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<RealUserRespDTO> getRealUserByUsername(@PathVariable("username") String username) {

        return Results.success(BeanUtil.toBean(userService.getUserByUserName(username), RealUserRespDTO.class));
    }

    @GetMapping("/api/shortlink/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    @PostMapping ("/api/shortlink/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();

    }
}

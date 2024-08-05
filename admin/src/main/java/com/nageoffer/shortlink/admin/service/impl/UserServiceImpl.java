package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public UserRespDTO getUserByUserName(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username); // 使用字段名代替方法引用
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        UserRespDTO result = new UserRespDTO();
        if(userDO == null){
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
        //TODO 验证当前用户名是否为登录用户

        LambdaUpdateWrapper<UserDO> eq = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class),eq);
    }

    /**
     * 注册用户
     * @param requestParam
     */
    public void register(UserRegisterReqDTO requestParam){
        if(hasUsername(requestParam.getUsername())){
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXSIT);
        }
        RLock lock = redissonClient.getLock(RedisCacheConstant.LOCK_USER_REGISTER_KEY+requestParam.getUsername());
        try{
            if(lock.tryLock()){
                try {

                    int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                    if(insert <1){
                        throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                    }
                } catch (DuplicateKeyException e){
                    throw new ClientException(UserErrorCodeEnum.USER_EXSIT);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
        }
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXSIT);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }

        String loginKey = "login_" + requestParam.getUsername();
        Boolean hasLogin = stringRedisTemplate.hasKey(loginKey);
        if (Boolean.TRUE.equals(hasLogin)) {
            throw new ClientException("用户已登录");
        }

        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(loginKey, "token", uuid);
        stringRedisTemplate.opsForHash().put(loginKey, "user", JSON.toJSONString(userDO));
        stringRedisTemplate.expire(loginKey, 30L, TimeUnit.DAYS);

        // 打印日志
        System.out.println("登录成功，存储的Key: " + loginKey + ", 存储的token: " + uuid);

        // 立即查询Redis，验证存储是否成功
        String storedToken = (String) stringRedisTemplate.opsForHash().get(loginKey, "token");
        System.out.println("存储后立即从Redis获取的token: " + storedToken);

        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        String loginKey = "login_" + username;
        String storedToken = (String) stringRedisTemplate.opsForHash().get(loginKey, "token");

        // 打印日志
        System.out.println("检查登录，查询的Key: " + loginKey);
        System.out.println("检查登录，传入的token: " + token);
        System.out.println("检查登录，从Redis获取的token: " + storedToken);

        return token.equals(storedToken);
    }

    @Override
    public Void logout(String username, String token) {
        if(checkLogin(username,token)){
            String loginKey = "login_" + username;
            stringRedisTemplate.delete(loginKey);
            return null;
        }
        throw new ClientException("用户未登录或用户Token不存在");
    }
}

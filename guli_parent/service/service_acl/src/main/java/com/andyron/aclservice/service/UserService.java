package com.andyron.aclservice.service;

import com.andyron.aclservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author andyron
 **/
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}

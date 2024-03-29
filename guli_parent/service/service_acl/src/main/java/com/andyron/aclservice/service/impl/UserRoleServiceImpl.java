package com.andyron.aclservice.service.impl;

import com.andyron.aclservice.entity.UserRole;
import com.andyron.aclservice.mapper.UserRoleMapper;
import com.andyron.aclservice.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

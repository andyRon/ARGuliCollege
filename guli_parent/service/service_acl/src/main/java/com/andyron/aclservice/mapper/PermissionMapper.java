package com.andyron.aclservice.mapper;

import com.andyron.aclservice.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author andyron
 **/
public interface PermissionMapper extends BaseMapper<Permission> {
    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}

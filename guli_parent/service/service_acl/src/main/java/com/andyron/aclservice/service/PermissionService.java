package com.andyron.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.andyron.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author andyron
 **/
public interface PermissionService extends IService<Permission> {
    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    //获取全部菜单
    List<Permission> queryAllMenu();

    //递归删除菜单
    void removeChildById(String id);

    //给角色分配权限
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);
}

package com.andyron.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.andyron.aclservice.entity.Permission;
import com.andyron.aclservice.entity.RolePermission;
import com.andyron.aclservice.entity.User;
import com.andyron.aclservice.helper.MenuHelper;
import com.andyron.aclservice.helper.PermissionHelper;
import com.andyron.aclservice.mapper.PermissionMapper;
import com.andyron.aclservice.service.PermissionService;
import com.andyron.aclservice.service.RolePermissionService;
import com.andyron.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andyron
 **/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        // 根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));

        for (Permission permission : allPermissionList) {
            for (RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }

        List<Permission> permissionList = buildPermission(allPermissionList);
        return permissionList;
    }

    /**
     * 根据用户id获取用户菜单
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            // 如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MenuHelper.build(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 获取全部菜单
     */
    @Override
    public List<Permission> queryAllMenu() {
        // 1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        // 2 把查询所有菜单list集合按照要求进行封装
        List<Permission> resultList = buildPermission(permissionList);
        return resultList;
    }

    /**
     * 把返回所有菜单list集合进行封装的方法
     */
    private static List<Permission> buildPermission(List<Permission> permissionList) {
        // 创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        // 把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for(Permission permissionNode : permissionList) {
            // 得到顶层菜单 pid=0菜单
            if("0".equals(permissionNode.getPid())) {
                // 设置顶层菜单的level是1
                permissionNode.setLevel(1);
                // 根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode, permissionList));
            }
        }
        return finalNode;
    }

    /**
     * 递归查找子节点
     */
    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        // 1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());

        // 2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for(Permission it : permissionList) {
            // 判断id和pid值是否相同
            if(permissionNode.getId().equals(it.getPid())) {
                // 把父菜单的level值+1
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                // 如果children为空，进行初始化操作
                if(permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                // 把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

    //============递归删除菜单==================================

    /**
     * 递归删除菜单
     */
    @Override
    public void removeChildById(String id) {
        // 1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        // 2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id,idList);
        // 把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    // 2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    /**
     * 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
     * @param id
     * @param idList
     */
    private void selectPermissionChildById(String id, List<String> idList) {
        // 查询菜单里面子菜单id
        QueryWrapper<Permission>  wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        // 把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            // 封装idList里面
            idList.add(item.getId());
            // 递归查询
            this.selectPermissionChildById(item.getId(),idList);
        });
    }

    //=========================给角色分配菜单=======================

    /**
     * 给角色分配菜单
     */
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for(String perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}

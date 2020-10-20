package com.peony.common.service;

import com.peony.common.entity.Permission;
import com.peony.common.entity.filter.PermissionFilter;

import java.util.List;

/**
 * @author hk
 * @date 2019/10/24
 */
public interface PermissionService extends EntityService<Permission, Integer, PermissionFilter> {

    /**
     * 通过角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限
     */
    List<Permission> findByRoleId(Integer roleId);

}

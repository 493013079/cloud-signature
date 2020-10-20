package com.peony.data.service.impl;

import com.google.common.collect.Lists;
import com.peony.common.entity.Permission;
import com.peony.common.entity.filter.PermissionFilter;
import com.peony.common.service.PermissionService;
import com.peony.data.converter.entity.PermissionConverter;
import com.peony.data.converter.predicate.PermissionFilterConverter;
import com.peony.data.entity.PermissionPO;
import com.peony.data.entity.RolePO;
import com.peony.data.repository.PermissionRepository;
import com.peony.data.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class PermissionServiceImpl extends AbstractEntityServiceImpl<Permission, PermissionPO, Integer, PermissionFilter> implements PermissionService {

    private final RoleRepository roleRepository;

    public PermissionServiceImpl(PermissionRepository entityRepository,
                                 PermissionConverter entityConverter,
                                 PermissionFilterConverter entityFilterConverter,
                                 RoleRepository roleRepository) {
        super(entityRepository, entityConverter, entityFilterConverter);
        this.roleRepository = roleRepository;
    }

    /**
     * 通过角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限
     */
    @Override
    public List<Permission> findByRoleId(Integer roleId) {
        return roleRepository.findById(roleId)
                .map(RolePO::getPermissions)
                .map(permissions -> permissions.stream().map(entityConverter::doForward).collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }

}

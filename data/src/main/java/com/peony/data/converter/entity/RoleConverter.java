package com.peony.data.converter.entity;

import com.peony.common.entity.Role;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.PermissionPO;
import com.peony.data.entity.RolePO;
import com.peony.data.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class RoleConverter implements EntityConverter<RolePO, Role> {

    @Override
    public Role doForward(RolePO rolePO) {
        Role role = ConvertUtils.convert(rolePO, Role.class);
        EntityUtils.copyAuditFields(rolePO, role);
        Optional.ofNullable(rolePO.getPermissions())
                .ifPresent(permissions -> {
                    List<Integer> permissionIds = permissions.stream()
                            .map(PermissionPO::getId)
                            .collect(Collectors.toList());
                    role.setPermissionIds(permissionIds);
                });
        return role;
    }

    @Override
    public RolePO doBackward(Role role) {
        RolePO rolePO = ConvertUtils.convert(role, RolePO.class);
        Optional.ofNullable(role.getPermissionIds())
                .ifPresent(permissionIds -> {
                    List<PermissionPO> permissions = permissionIds.stream()
                            .map(PermissionPO::new)
                            .collect(Collectors.toList());
                    rolePO.setPermissions(permissions);
                });
        return rolePO;
    }

}

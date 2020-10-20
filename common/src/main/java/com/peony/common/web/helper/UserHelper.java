package com.peony.common.web.helper;

import com.google.common.collect.Lists;
import com.peony.common.entity.Permission;
import com.peony.common.entity.Role;
import com.peony.common.entity.User;
import com.peony.common.entity.field.PermissionKey;
import com.peony.common.entity.field.RoleType;
import com.peony.common.service.PermissionService;
import com.peony.common.service.RoleService;
import com.peony.common.util.StringUtils;
import com.peony.common.web.Logical;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户帮助类
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@ConditionalOnBean({RoleService.class, PermissionService.class})
@Component
public class UserHelper {

    private final RoleService roleService;

    private final PermissionService permissionService;

    public UserHelper(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    /**
     * 加密密码
     *
     * @param originPassword 原始密码
     * @return 加密后的密码
     */
    public String encodePassword(String originPassword) {
        String salt = "template:";
        return StringUtils.md5(salt + originPassword);
    }

    /**
     * 判断用户是否具有该角色
     *
     * @param user      用户
     * @param roleTypes 角色
     * @return 是否具有该角色
     */
    public boolean hasRole(@Nonnull User user, RoleType[] roleTypes) {
        if (roleTypes == null || roleTypes.length <= 0) {
            return true;
        }
        Optional<Role> optionalRole = getRole(user);
        if (!optionalRole.isPresent()) {
            return false;
        }
        Role role = optionalRole.get();
        return Arrays.asList(roleTypes).contains(role.getType());
    }

    /**
     * 判断用户是否具有该权限
     *
     * @param user           用户
     * @param permissionKeys 权限
     * @param logical        与，或
     * @return 是否具有该权限
     */
    public boolean hasPermissions(@Nonnull User user,
                                  @Nullable PermissionKey[] permissionKeys,
                                  @Nonnull Logical logical) {
        if (permissionKeys == null || permissionKeys.length <= 0) {
            return true;
        }

        List<String> needPermissionKeys = Stream.of(permissionKeys)
                .map(Enum::name)
                .collect(Collectors.toList());

        List<Permission> permissions = getPermissions(user);
        List<String> userPermissionKeys = permissions.stream()
                .map(Permission::getKey)
                .collect(Collectors.toList());
        if (Logical.AND.equals(logical)) {
            return userPermissionKeys.containsAll(needPermissionKeys);
        }
        if (Logical.OR.equals(logical)) {
            return needPermissionKeys.stream().anyMatch(userPermissionKeys::contains);
        }
        return false;
    }

    /**
     * 获取用户角色
     *
     * @param user 用户
     * @return 角色
     */
    @Nonnull
    public Optional<Role> getRole(@Nonnull User user) {
        return Optional.ofNullable(user.getRoleId()).map(roleService::findById);
    }

    /**
     * 获取用户权限
     *
     * @param user 用户
     * @return 权限
     */
    @Nonnull
    public List<Permission> getPermissions(@Nonnull User user) {
        return Optional.ofNullable(user.getRoleId())
                .map(permissionService::findByRoleId)
                .orElse(Lists.newArrayList());
    }

}

package com.peony.common.entity.field;

/**
 * @author hk
 * @date 2019/10/24
 */
public enum PermissionKey {

    /**
     * 系统管理
     */
    SYSTEM,
    /**
     * 组织管理
     */
    SYSTEM_ORGANIZATION,
    SYSTEM_ORGANIZATION_ADD,
    SYSTEM_ORGANIZATION_UPDATE,
    SYSTEM_ORGANIZATION_DELETE,

    /**
     * 角色管理
     */
    SYSTEM_ROLE,
    SYSTEM_ROLE_ADD,
    SYSTEM_ROLE_UPDATE,
    SYSTEM_ROLE_DELETE,

    /**
     * 用户管理
     */
    SYSTEM_USER,
    SYSTEM_USER_ADD,
    SYSTEM_USER_UPDATE,
    SYSTEM_USER_DELETE,

    /**
     * 推送消息
     */
    SYSTEM_MESSAGE,

    /**
     * 审计日志
     */
    AUDIT_LOG
}

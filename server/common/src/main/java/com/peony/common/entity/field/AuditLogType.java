package com.peony.common.entity.field;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实体操作类型：添加，修改，删除
 *
 * @author YINJUNJIA
 * @date 2019/10/18 15:52
 */
@AllArgsConstructor
@Getter
public enum AuditLogType {

    /**
     * 删除
     */
    DELETE,

    /**
     * 添加
     */
    ADD,

    /**
     * 更新
     */
    UPDATE,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 登出
     */
    LOGOUT

}

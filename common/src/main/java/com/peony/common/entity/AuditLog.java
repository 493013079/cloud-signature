package com.peony.common.entity;

import com.peony.common.entity.field.AuditLogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * 审计日志
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog implements Entity {

    /**
     * 审计日志ID
     */
    private Integer id;

    /**
     * 操作类型
     */
    private AuditLogType type;

    /**
     * 对象类型
     */
    private String objectType;

    /**
     * 操作对象ID
     */
    private String objectId;

    /**
     * 操作用户ID
     */
    private Integer userId;

    /**
     * 操作用户姓名
     */
    private String userName;

    /**
     * 创建时间
     */
    private ZonedDateTime createdDate;

}

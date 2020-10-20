package com.peony.data.entity;

import com.peony.common.entity.field.AuditLogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author hk
 * @date 2019/11/21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLogPO implements SoftDeletePO {

    /**
     * 主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    private AuditLogType type;

    /**
     * 对象类型
     */
    private String objectType;

    /**
     * 操作对象 id
     */
    private String objectId;

    /**
     * 操作用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPO user;

    @Column(updatable = false)
    private ZonedDateTime createdDate;

    private Boolean active = true;

}

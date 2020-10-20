package com.peony.common.entity.filter;

import com.peony.common.entity.AuditLog;
import com.peony.common.entity.field.AuditLogType;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author hk
 * @date 2019/10/24 14:12
 */
@Data
@Builder
public class AuditLogFilter implements EntityFilter<AuditLog> {

    private String keyword;

    private Integer userId;

    private String organizationPath;

    private AuditLogType type;

    private String objectId;

    private String objectType;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

}

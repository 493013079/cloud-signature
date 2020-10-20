package com.peony.data.converter.entity;

import com.peony.common.entity.AuditLog;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.AuditLogPO;
import com.peony.data.entity.UserPO;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class AuditLogConverter implements EntityConverter<AuditLogPO, AuditLog> {

    @Override
    public AuditLog doForward(AuditLogPO auditLogPO) {
        AuditLog auditLog = ConvertUtils.convert(auditLogPO, AuditLog.class);
        Optional.ofNullable(auditLogPO.getUser())
                .ifPresent(userPO -> {
                    auditLog.setUserId(userPO.getId());
                    auditLog.setUserName(userPO.getName());
                });
        return auditLog;
    }

    @Override
    public AuditLogPO doBackward(AuditLog auditLog) {
        AuditLogPO auditLogPO = ConvertUtils.convert(auditLog, AuditLogPO.class);
        Optional.ofNullable(auditLog.getUserId())
                .map(UserPO::new)
                .ifPresent(auditLogPO::setUser);
        return auditLogPO;
    }

}

package com.peony.data.config;

import com.peony.common.entity.field.AuditLogType;
import com.peony.common.service.AuditLogService;
import com.peony.data.entity.AuditablePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author 辛毅
 * @date 2019/11/25
 */
@Slf4j
@Component
public class AuditLogListener {

    private static AuditLogService auditLogService;

    @Autowired
    public void setAuditLogService(AuditLogService auditLogService) {
        AuditLogListener.auditLogService = auditLogService;
    }

    @PrePersist
    public void touchForCreate(AuditablePO target) {
        log.info("PrePersist: " + target.getClass().getName() + " " + target.getId());
    }

    @PreUpdate
    public void touchForUpdate(AuditablePO target) {
        log.info("PreUpdate: " + target.getClass().getName() + " " + target.getId());
    }

    @PostPersist
    public void touchForCreated(AuditablePO target) {
        log.info("PostPersist: " + target.getClass().getName() + " " + target.getId());
        saveAuditLog(target, AuditLogType.ADD);
    }

    @PostUpdate
    public void touchForUpdated(AuditablePO target) {
        log.info("PostUpdate: " + target.getClass().getName() + " " + target.getId());
        saveAuditLog(target, target.getActive() ? AuditLogType.UPDATE : AuditLogType.DELETE);
    }

    private void saveAuditLog(AuditablePO target, AuditLogType auditLogType) {
/*        Table table = target.getClass().getAnnotation(Table.class);
        AuditLog auditLog = AuditLog.builder()
                .objectId(String.valueOf(target.getId()))
                .objectType(table.name())
                .type(auditLogType)
                .userId(Optional.ofNullable(target.getLastModifiedBy()).map(UserPO::getId).orElse(null))
                .createdDate(target.getLastModifiedDate())
                .build();

         auditLogService.save(auditLog);*/
    }

}

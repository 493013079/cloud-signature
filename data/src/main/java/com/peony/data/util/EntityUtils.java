package com.peony.data.util;

import com.peony.common.entity.Auditable;
import com.peony.data.entity.AuditablePO;
import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/27
 */
@UtilityClass
public class EntityUtils {

    public void copyAuditFields(AuditablePO auditablePO, Auditable auditable) {
        Optional.ofNullable(auditablePO.getCreatedBy())
                .ifPresent(createBy -> {
                    auditable.setCreatedById(createBy.getId());
                    auditable.setCreatedByName(createBy.getName());
                });
        Optional.ofNullable(auditablePO.getLastModifiedBy())
                .ifPresent(lastModifiedBy -> {
                    auditable.setLastModifiedById(lastModifiedBy.getId());
                    auditable.setLastModifiedByName(lastModifiedBy.getName());
                });
        auditable.setCreatedDate(auditablePO.getCreatedDate());
        auditable.setLastModifiedDate(auditablePO.getLastModifiedDate());
    }

}

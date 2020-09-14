package com.peony.data.entity;

import java.time.ZonedDateTime;

/**
 * @author 辛毅
 * @date 2019/11/25
 */
public interface AuditablePO<ID> extends SoftDeletePO {

    ID getId();

    UserPO getCreatedBy();

    ZonedDateTime getCreatedDate();

    UserPO getLastModifiedBy();

    ZonedDateTime getLastModifiedDate();

}

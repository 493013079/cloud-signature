package com.peony.data.repository;

import com.peony.data.entity.AuditLogPO;
import org.springframework.stereotype.Repository;

/**
 * @author hk
 * @date 2019/11/21
 */
@Repository
public interface AuditLogRepository extends EntityRepository<AuditLogPO, Integer> {

}

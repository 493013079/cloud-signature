package com.peony.data.service.impl;

import com.peony.common.entity.AuditLog;
import com.peony.common.entity.filter.AuditLogFilter;
import com.peony.common.service.AuditLogService;
import com.peony.data.converter.entity.AuditLogConverter;
import com.peony.data.converter.predicate.AuditLogFilterConverter;
import com.peony.data.entity.AuditLogPO;
import com.peony.data.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/11/21
 */
@Service
public class AuditLogServiceImpl extends AbstractEntityServiceImpl<AuditLog, AuditLogPO, Integer, AuditLogFilter> implements AuditLogService {

    public AuditLogServiceImpl(AuditLogRepository entityRepository,
                               AuditLogConverter entityConverter,
                               AuditLogFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

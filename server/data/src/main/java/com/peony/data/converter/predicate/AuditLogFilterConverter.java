package com.peony.data.converter.predicate;

import com.peony.common.entity.field.AuditLogType;
import com.peony.common.entity.filter.AuditLogFilter;
import com.peony.data.entity.QAuditLogPO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author hk
 * @date 2019/11/21
 */
@Component
public class AuditLogFilterConverter implements EntityFilterToPredicateConverter<AuditLogFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull AuditLogFilter auditLogFilter) {
        Optional<String> optionalKeyword = Optional.ofNullable(auditLogFilter.getKeyword());
        Optional<Integer> optionalUserId = Optional.ofNullable(auditLogFilter.getUserId());
        Optional<String> optionalOrganizationPath = Optional.ofNullable(auditLogFilter.getOrganizationPath());
        Optional<AuditLogType> optionalType = ofNullable(auditLogFilter.getType());
        Optional<String> optionalObjectId = ofNullable(auditLogFilter.getObjectId());
        Optional<String> optionalObjectType = ofNullable(auditLogFilter.getObjectType());
        Optional<ZonedDateTime> optionalStartTime = Optional.ofNullable(auditLogFilter.getStartTime());
        Optional<ZonedDateTime> optionalEndTime = Optional.ofNullable(auditLogFilter.getEndTime());

        QAuditLogPO auditLogPO = QAuditLogPO.auditLogPO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        optionalKeyword.map(auditLogPO.user.name::contains).ifPresent(booleanBuilder::or);
        optionalUserId.map(auditLogPO.user.id::eq).ifPresent(booleanBuilder::and);
        optionalOrganizationPath.map(auditLogPO.user.organization.path::startsWith).ifPresent(booleanBuilder::and);
        optionalType.map(auditLogPO.type::eq).ifPresent(booleanBuilder::and);
        optionalObjectId.map(auditLogPO.objectId::eq).ifPresent(booleanBuilder::and);
        optionalObjectType.map(auditLogPO.objectType::eq).ifPresent(booleanBuilder::and);
        optionalStartTime.map(auditLogPO.createdDate::goe).ifPresent(booleanBuilder::and);
        optionalEndTime.map(auditLogPO.createdDate::lt).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}

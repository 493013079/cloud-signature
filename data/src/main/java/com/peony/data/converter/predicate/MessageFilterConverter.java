package com.peony.data.converter.predicate;

import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import com.peony.common.entity.filter.MessageFilter;
import com.peony.data.entity.QMessagePO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author hk
 * @date 2019/10/24
 */
@Component
public class MessageFilterConverter implements EntityFilterToPredicateConverter<MessageFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull MessageFilter messageFilter) {
        Optional<String> optionalKeyword = Optional.ofNullable(messageFilter.getKeyword());
        Optional<Integer> optionalUserId = Optional.ofNullable(messageFilter.getUserId());
        Optional<String> optionalOrganizationPath = Optional.ofNullable(messageFilter.getOrganizationPath());
        Optional<MessageType> optionalMessageType = Optional.ofNullable(messageFilter.getType());
        Optional<ReadStatus> optionalReadStatus = Optional.ofNullable(messageFilter.getReadStatus());
        Optional<ZonedDateTime> optionalStartTime = Optional.ofNullable(messageFilter.getStartTime());
        Optional<ZonedDateTime> optionalEndTime = Optional.ofNullable(messageFilter.getEndTime());

        QMessagePO messagePO = QMessagePO.messagePO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        optionalKeyword.map(messagePO.user.name::contains).ifPresent(booleanBuilder::or);
        optionalKeyword.map(messagePO.content::contains).ifPresent(booleanBuilder::or);
        optionalUserId.map(messagePO.user.id::eq).ifPresent(booleanBuilder::and);
        optionalOrganizationPath.map(messagePO.user.organization.path::startsWith).ifPresent(booleanBuilder::and);
        optionalMessageType.map(messagePO.type::eq).ifPresent(booleanBuilder::and);
        optionalReadStatus.map(messagePO.readStatus::eq).ifPresent(booleanBuilder::and);
        optionalStartTime.map(messagePO.createdDate::goe).ifPresent(booleanBuilder::and);
        optionalEndTime.map(messagePO.createdDate::lt).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}
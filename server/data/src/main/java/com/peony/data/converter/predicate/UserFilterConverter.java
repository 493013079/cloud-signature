package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.UserFilter;
import com.peony.data.entity.QUserPO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hk
 * @date 2019/10/24
 */
@Component
public class UserFilterConverter implements EntityFilterToPredicateConverter<UserFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull UserFilter userFilter) {
        Optional<String> optionalKeyword = Optional.ofNullable(userFilter.getKeyword());
        Optional<String> optionalAccount = Optional.ofNullable(userFilter.getAccount());
        Optional<Integer> optionalRoleId = Optional.ofNullable(userFilter.getRoleId());
        Optional<Integer> optionalOrganizationId = Optional.ofNullable(userFilter.getOrganizationId());
        Optional<String> optionalOrganizationPath = Optional.ofNullable(userFilter.getOrganizationPath());
        Optional<String> optionalEmail = Optional.ofNullable(userFilter.getEmail());
        Optional<String> optionalPhoneNumber = Optional.ofNullable(userFilter.getPhoneNumber());

        QUserPO qUserPO = QUserPO.userPO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        optionalKeyword.map(qUserPO.name::contains).ifPresent(booleanBuilder::or);
        optionalKeyword.map(qUserPO.account::containsIgnoreCase).ifPresent(booleanBuilder::or);
        optionalAccount.map(qUserPO.account::eq).ifPresent(booleanBuilder::and);
        optionalRoleId.map(qUserPO.role.id::eq).ifPresent(booleanBuilder::and);
        optionalOrganizationId.map(qUserPO.organization.id::eq).ifPresent(booleanBuilder::and);
        optionalOrganizationPath.map(qUserPO.organization.path::contains).ifPresent(booleanBuilder::and);
        optionalPhoneNumber.map(qUserPO.phoneNumber::eq).ifPresent(booleanBuilder::and);
        optionalEmail.map(qUserPO.email::eq).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}
package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.PermissionFilter;
import com.peony.data.entity.QPermissionPO;
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
public class PermissionFilterConverter implements EntityFilterToPredicateConverter<PermissionFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull PermissionFilter permissionFilter) {
        QPermissionPO permissionPO = QPermissionPO.permissionPO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Optional<String> permissionKey = Optional.ofNullable(permissionFilter.getPermissionKey());
        Optional<Boolean> active = Optional.ofNullable(permissionFilter.getActive());
        permissionKey.map(permissionPO.key::eq).ifPresent(booleanBuilder::and);
        active.map(permissionPO.active::eq).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}
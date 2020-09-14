package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.RoleFilter;
import com.peony.data.entity.QRolePO;
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
public class RoleFilterConverter implements EntityFilterToPredicateConverter<RoleFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull RoleFilter roleFilter) {
        Optional<String> optionalKeyword = Optional.ofNullable(roleFilter.getKeyword());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QRolePO rolePO = QRolePO.rolePO;
        optionalKeyword.map(rolePO.name::contains).ifPresent(booleanBuilder::or);
        optionalKeyword.map(rolePO.description::contains).ifPresent(booleanBuilder::or);
        return booleanBuilder;
    }

}
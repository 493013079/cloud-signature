package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.DictFilter;
import com.peony.data.entity.QDictPO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author hk
 * @date 2019/10/24
 */
@Component
public class DictFilterConverter implements EntityFilterToPredicateConverter<DictFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull DictFilter dictFilter) {
        Optional<String> optionalType = Optional.ofNullable(dictFilter.getType()).filter(e -> !StringUtils.isEmpty(e));
        Optional<String> optionalKey = Optional.ofNullable(dictFilter.getKey()).filter(e -> !StringUtils.isEmpty(e));

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDictPO qDictPO = QDictPO.dictPO;
        optionalType.map(qDictPO.type::eq).ifPresent(booleanBuilder::and);
        optionalKey.map(qDictPO.key::eq).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}
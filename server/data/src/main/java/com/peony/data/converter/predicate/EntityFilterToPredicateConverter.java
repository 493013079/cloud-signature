package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.EntityFilter;
import com.querydsl.core.types.Predicate;
import org.springframework.core.convert.converter.Converter;

public interface EntityFilterToPredicateConverter<S extends EntityFilter> extends Converter<S, Predicate> {

}

package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.OrganizationFilter;
import com.peony.data.entity.QOrganizationPO;
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
public class OrganizationFilterConverter implements EntityFilterToPredicateConverter<OrganizationFilter> {

    @NonNull
    @Override
    public Predicate convert(@NonNull OrganizationFilter organizationFilter) {
        Optional<String> optionalKeyword = Optional.ofNullable(organizationFilter.getKeyword())
                .filter(e -> !StringUtils.isEmpty(e));
        Optional<String> optionalPath = Optional.ofNullable(organizationFilter.getPath())
                .filter(e -> !StringUtils.isEmpty(e));
        Optional<Integer> optionalParentId = Optional.ofNullable(organizationFilter.getParentId());

        QOrganizationPO organizationPO = QOrganizationPO.organizationPO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        optionalKeyword.map(organizationPO.name::contains).ifPresent(booleanBuilder::and);
        optionalPath.map(organizationPO.path::startsWith).ifPresent(booleanBuilder::and);
        optionalParentId.map(organizationPO.parent.id::eq).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}
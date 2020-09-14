package com.peony.data.service.impl;

import com.peony.common.entity.Organization;
import com.peony.common.entity.filter.OrganizationFilter;
import com.peony.common.service.OrganizationService;
import com.peony.data.converter.entity.OrganizationConverter;
import com.peony.data.converter.predicate.OrganizationFilterConverter;
import com.peony.data.entity.OrganizationPO;
import com.peony.data.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class OrganizationServiceImpl extends AbstractEntityServiceImpl<Organization, OrganizationPO, Integer, OrganizationFilter> implements OrganizationService {

    public OrganizationServiceImpl(OrganizationRepository entityRepository,
                                   OrganizationConverter entityConverter,
                                   OrganizationFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

package com.peony.data.service.impl;

import com.peony.common.entity.Role;
import com.peony.common.entity.filter.RoleFilter;
import com.peony.common.service.RoleService;
import com.peony.data.converter.entity.RoleConverter;
import com.peony.data.converter.predicate.RoleFilterConverter;
import com.peony.data.entity.RolePO;
import com.peony.data.repository.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class RoleServiceImpl extends AbstractEntityServiceImpl<Role, RolePO, Integer, RoleFilter> implements RoleService {

    public RoleServiceImpl(RoleRepository entityRepository,
                           RoleConverter entityConverter,
                           RoleFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

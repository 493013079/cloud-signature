package com.peony.data.service.impl;

import com.peony.common.entity.User;
import com.peony.common.entity.filter.UserFilter;
import com.peony.common.service.UserService;
import com.peony.data.converter.entity.UserConverter;
import com.peony.data.converter.predicate.UserFilterConverter;
import com.peony.data.entity.UserPO;
import com.peony.data.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class UserServiceImpl extends AbstractEntityServiceImpl<User, UserPO, Integer, UserFilter> implements UserService {

    public UserServiceImpl(UserRepository entityRepository,
                           UserConverter entityConverter,
                           UserFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

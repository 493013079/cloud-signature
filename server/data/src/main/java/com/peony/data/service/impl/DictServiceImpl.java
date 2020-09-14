package com.peony.data.service.impl;

import com.peony.common.entity.Dict;
import com.peony.common.entity.filter.DictFilter;
import com.peony.common.service.DictService;
import com.peony.data.converter.entity.DictConverter;
import com.peony.data.converter.predicate.DictFilterConverter;
import com.peony.data.entity.DictPO;
import com.peony.data.repository.DictRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class DictServiceImpl extends AbstractEntityServiceImpl<Dict, DictPO, Integer, DictFilter> implements DictService {

    public DictServiceImpl(DictRepository entityRepository,
                           DictConverter entityConverter,
                           DictFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

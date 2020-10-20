package com.peony.data.service.impl;

import com.peony.common.entity.Signature;
import com.peony.common.entity.filter.SignatureFilter;
import com.peony.common.service.SignatureService;
import com.peony.data.converter.entity.SignatureConvert;
import com.peony.data.converter.predicate.SignatureFilterConvert;
import com.peony.data.entity.SignaturePO;
import com.peony.data.repository.SignatureRepository;
import org.springframework.stereotype.Service;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Service
public class SignatureServiceImpl extends AbstractEntityServiceImpl<Signature, SignaturePO, String, SignatureFilter> implements SignatureService {

    public SignatureServiceImpl(SignatureRepository entityRepository,
                                SignatureConvert entityConverter,
                                SignatureFilterConvert entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

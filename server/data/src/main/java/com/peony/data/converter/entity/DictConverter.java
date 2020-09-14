package com.peony.data.converter.entity;

import com.peony.common.entity.Dict;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.DictPO;
import org.springframework.stereotype.Component;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class DictConverter implements EntityConverter<DictPO, Dict> {

    @Override
    public Dict doForward(DictPO dictPO) {
        return ConvertUtils.convert(dictPO, Dict.class);
    }

    @Override
    public DictPO doBackward(Dict dict) {
        return ConvertUtils.convert(dict, DictPO.class);
    }

}

package com.peony.common.entity.filter;

import com.peony.common.entity.Dict;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 * @date 2019/10/24
 */

@Data
@Builder
public class DictFilter implements EntityFilter<Dict> {

    private String type;

    private String key;

}

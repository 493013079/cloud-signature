package com.peony.common.entity.filter;

import com.peony.common.entity.Role;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
public class RoleFilter implements EntityFilter<Role> {

    private String keyword;

}

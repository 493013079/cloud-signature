package com.peony.common.entity.filter;

import com.peony.common.entity.Permission;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
public class PermissionFilter implements EntityFilter<Permission> {

    private String permissionKey;

    private Boolean active;

}

package com.peony.data.converter.entity;

import com.peony.common.entity.Permission;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.PermissionPO;
import org.springframework.stereotype.Component;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class PermissionConverter implements EntityConverter<PermissionPO, Permission> {

    @Override
    public Permission doForward(PermissionPO permissionPO) {
        return ConvertUtils.convert(permissionPO, Permission.class);
    }

    @Override
    public PermissionPO doBackward(Permission permission) {
        return ConvertUtils.convert(permission, PermissionPO.class);
    }

}

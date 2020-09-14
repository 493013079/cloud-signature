package com.peony.data.converter.entity;

import com.peony.common.entity.User;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.OrganizationPO;
import com.peony.data.entity.RolePO;
import com.peony.data.entity.UserPO;
import com.peony.data.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class UserConverter implements EntityConverter<UserPO, User> {

    @Override
    public User doForward(UserPO userPO) {
        User user = ConvertUtils.convert(userPO, User.class);
        Optional.ofNullable(userPO.getRole())
                .ifPresent(rolePO -> {
                    user.setRoleId(rolePO.getId());
                    user.setRoleName(rolePO.getName());
                });
        Optional.ofNullable(userPO.getOrganization())
                .ifPresent(organizationPO -> {
                    user.setOrganizationId(organizationPO.getId());
                    user.setOrganizationName(organizationPO.getName());
                    user.setOrganizationPath(organizationPO.getPath());
                });
        EntityUtils.copyAuditFields(userPO, user);
        return user;
    }

    @Override
    public UserPO doBackward(User user) {
        UserPO userPO = ConvertUtils.convert(user, UserPO.class);
        Optional.ofNullable(user.getRoleId())
                .map(RolePO::new)
                .ifPresent(userPO::setRole);
        Optional.ofNullable(user.getOrganizationId())
                .map(OrganizationPO::new)
                .ifPresent(userPO::setOrganization);
        return userPO;
    }

}

package com.peony.data.converter.entity;

import com.peony.common.entity.Organization;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.OrganizationPO;
import com.peony.data.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class OrganizationConverter implements EntityConverter<OrganizationPO, Organization> {

    @Override
    public Organization doForward(OrganizationPO organizationPO) {
        Organization organization = ConvertUtils.convert(organizationPO, Organization.class);
        Optional.ofNullable(organizationPO.getParent())
                .ifPresent(parent -> {
                    organization.setParentId(parent.getId());
                    organization.setParentName(parent.getName());
                });
        EntityUtils.copyAuditFields(organizationPO, organization);
        return organization;
    }

    @Override
    public OrganizationPO doBackward(Organization organization) {
        OrganizationPO organizationPO = ConvertUtils.convert(organization, OrganizationPO.class);
        Optional.ofNullable(organization.getParentId())
                .map(OrganizationPO::new)
                .ifPresent(organizationPO::setParent);
        return organizationPO;
    }

}

package com.peony.web.formatter;

import com.peony.common.entity.Organization;
import com.peony.common.service.OrganizationService;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

/**
 * @author hk
 */
@Component
public class OrganizationFormatter implements Formatter<Organization> {

    private final OrganizationService OrganizationService;

    public OrganizationFormatter(OrganizationService organizationService) {
        OrganizationService = organizationService;
    }

    @Override
    @NonNull
    public Organization parse(@NonNull String id, @NonNull Locale locale) {
        Integer organizationId = Integer.valueOf(id);
        return Optional.of(OrganizationService.findById(organizationId))
                .orElseThrow(() -> new RestException(RestErrorCode.ORGANIZATION_NOT_EXIST));
    }

    @Override
    @NonNull
    public String print(Organization organization, @NonNull Locale locale) {
        return String.valueOf(organization.getId());
    }

}

package com.peony.common.entity.filter;

import com.peony.common.entity.Organization;
import lombok.Builder;
import lombok.Data;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
public class OrganizationFilter implements EntityFilter<Organization> {

    private String keyword;

    private Integer parentId;

    private String path;

}

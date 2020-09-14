package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peony.common.entity.field.OrganizationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author hk
 * @date 2019/10/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationVO extends AbstractAuditableVO {

    @ApiModelProperty(value = "组织ID")
    private Integer id;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "父级组织ID")
    private Integer parentId;

    @ApiModelProperty(value = "父级组织名称")
    private String parentName;

    @ApiModelProperty(value = "组织类型")
    private OrganizationType type;

}

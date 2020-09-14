package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peony.common.entity.field.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

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
public class RoleVO extends AbstractAuditableVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色类型")
    private RoleType type;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "权限列表")
    private List<Integer> permissionIds;

}

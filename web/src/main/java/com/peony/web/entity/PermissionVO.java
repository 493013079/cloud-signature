package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peony.common.entity.field.PermissionKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hk
 * @date 2019/10/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "权限父id")
    private Integer parentId;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限类型")
    private PermissionKey permissionKey;

}

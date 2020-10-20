package com.peony.web.entity.form.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hk
 * @date 2019/10/25
 */
@ApiModel(value = "权限对象", description = "权限对象VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFormVO {

    @ApiModelProperty(value = "权限vo集合", required = true)
    private List<Integer> permissionIds;

}

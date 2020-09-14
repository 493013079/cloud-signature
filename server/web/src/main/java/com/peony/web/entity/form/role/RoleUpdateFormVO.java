package com.peony.web.entity.form.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author hk
 * @date 2019/10/24
 */
@ApiModel(value = "角色对象更新", description = "角色对象updateVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateFormVO {

    @NotBlank
    @ApiModelProperty(value = "名字", example = "", required = true)
    private String name;

    @ApiModelProperty(value = "描述", example = "", required = true)
    private String description;

    @NotEmpty
    @ApiModelProperty(value = "权限", example = "", required = true)
    private List<Integer> permissionIds;

}

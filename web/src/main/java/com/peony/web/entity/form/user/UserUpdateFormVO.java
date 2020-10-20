package com.peony.web.entity.form.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hk
 * @date 2019/10/24
 */
@ApiModel(value = "用户对象更新", description = "用户对象updateVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateFormVO {

    @NotBlank
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "组织id", required = true)
    private Integer organizationId;

    @NotNull
    @ApiModelProperty(value = "角色id", required = true)
    private Integer roleId;

    @ApiModelProperty(value = "手机号", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

}

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
@ApiModel(value = "用户对象添加", description = "用户对象addVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddFormVO {

    @NotBlank
    @ApiModelProperty(value = "名字", example = "", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "账号", example = "", required = true)
    private String account;

    @NotBlank
    @ApiModelProperty(value = "密码", example = "", required = true)
    private String password;

    @NotNull
    @ApiModelProperty(value = "组织id", example = "", required = true)
    private Integer organizationId;

    @NotNull
    @ApiModelProperty(value = "角色id", example = "", required = true)
    private Integer roleId;

    @ApiModelProperty(value = "手机号", example = "", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱", example = "", required = true)
    private String email;

}

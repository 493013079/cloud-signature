package com.peony.web.entity.form.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hk
 * @date 2019/10/24
 */
@ApiModel(value = "登陆表单", description = "登陆表单VO")
@Data
public class LoginFormVO {

    @ApiModelProperty(value = "账号", example = "", required = true)
    @NotBlank
    private String account;

    @ApiModelProperty(value = "密码", example = "", required = true)
    @NotBlank
    private String password;

}

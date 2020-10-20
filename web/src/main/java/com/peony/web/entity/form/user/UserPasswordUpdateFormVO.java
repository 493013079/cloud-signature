package com.peony.web.entity.form.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hk
 * @date 2019/10/31
 */
@Data
public class UserPasswordUpdateFormVO {

    @NotBlank
    public String password;

}

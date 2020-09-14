package com.peony.web.entity.form.organization;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author hk
 * @date 2019/10/25
 */
@ApiModel(value = "组织对象更新", description = "组织对象updateVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUpdateFormVO {

    @NotBlank
    @ApiModelProperty(value = "组织名字", required = true)
    private String name;

}

package com.peony.web.entity.form.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.peony.common.entity.Organization;
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
@ApiModel(value = "组织对象添加", description = "组织对象addVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAddFormVO {

    @NotBlank
    @ApiModelProperty(value = "组织名字", required = true)
    private String name;

    @ApiModelProperty(value = "父级组织ID", required = true)
    @JsonProperty("parentId")
    private Organization parent;

}

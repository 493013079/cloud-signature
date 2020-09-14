package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hk
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthVO {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "授权TOKEN")
    private String token;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "用户所属机构ID")
    private Integer organizationId;

    @ApiModelProperty(value = "用户所属机构名称")
    private String organizationName;

    @ApiModelProperty(value = "用户角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "用户角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户权限KEY列表")
    private List<String> permissionKeys;

    @ApiModelProperty(value = "用户权限名称列表")
    private List<String> permissionNames;

}

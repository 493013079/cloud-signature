package com.peony.common.entity;

import com.peony.common.entity.field.UserType;
import lombok.*;

/**
 * 用户
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractAuditable implements Entity {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户类型
     */
    private UserType type;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 组织ID
     */
    private Integer organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 组织路径
     */
    private String organizationPath;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

}

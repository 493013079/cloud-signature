package com.peony.common.entity;

import com.peony.common.entity.field.RoleType;
import lombok.*;

import java.util.List;

/**
 * 角色
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractAuditable implements Entity {

    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色类型
     */
    private RoleType type;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 权限
     */
    private List<Integer> permissionIds;

}

package com.peony.common.entity;

import com.peony.common.entity.field.OrganizationType;
import lombok.*;

/**
 * 组织
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Organization extends AbstractAuditable implements Entity {

    /**
     * 组织ID
     */
    private Integer id;

    /**
     * 父级组织ID
     */
    private Integer parentId;

    /**
     * 父级组织名称
     */
    private String parentName;

    /**
     * 组织PATH
     */
    private String path;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织类型
     */
    private OrganizationType type;

}

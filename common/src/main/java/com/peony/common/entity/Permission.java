package com.peony.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Entity {

    /**
     * 权限ID
     */
    private Integer id;

    /**
     * 父级权限ID
     */
    private Integer parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String key;

}

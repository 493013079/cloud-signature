package com.peony.data.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "permission")
public class PermissionPO extends BasePO<Integer> {

    public PermissionPO(Integer id) {
        this.setId(id);
    }

    /**
     * 主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限父id
     */
    private Integer parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限Key
     */
    private String key;

    /**
     * 权限描述
     */
    private String description;

}

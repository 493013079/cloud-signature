package com.peony.data.entity;

import com.peony.common.entity.field.RoleType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "role")
public class RolePO extends BasePO<Integer> {

    public RolePO(Integer id) {
        this.setId(id);
    }

    /**
     * 主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    @ToString.Exclude
    private List<PermissionPO> permissions;

}

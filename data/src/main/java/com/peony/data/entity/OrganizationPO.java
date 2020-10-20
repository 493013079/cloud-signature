package com.peony.data.entity;

import com.peony.common.entity.field.OrganizationType;
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
@Table(name = "organization")
public class OrganizationPO extends BasePO<Integer> {

    public OrganizationPO(Integer id) {
        this.setId(id);
    }

    /**
     * 主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 父级组织id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationPO parent;

    /**
     * 组织类型
     */
    @Enumerated(EnumType.STRING)
    private OrganizationType type;

    /**
     * 结构树路径
     */
    private String path;

}

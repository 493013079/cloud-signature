package com.peony.data.entity;

import com.peony.common.entity.field.UserType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
public class UserPO extends BasePO<Integer> {

    public UserPO(Integer id) {
        this.setId(id);
    }

    /**
     * 主键，自增
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 组织id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationPO organization;

    /**
     * 姓名
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
     * 角色id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private RolePO role;

    /**
     * 用户类型
     */
    @Enumerated(EnumType.STRING)
    private UserType type;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

}

package com.peony.data.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "tickets")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
public class TicketsPO extends BasePO<String> {

    public TicketsPO(String id) {
        this.setId(id);
    }

    /**
     * 主键，snowFlake算法生成
     */
/*    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.template.data.entity.MyIdGenerator")*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.peony.common.util.CustomIDGenerator")
    private String id;

    /**
     * 票据URL
     */
    @ToString.Exclude
    @NotEmpty
    private String targetURL;

    //    @OneToMany(mappedBy="ticket",cascade= CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval = true)
    @OneToMany(mappedBy = "ticket")
    private List<SignaturePO> signatures;

}

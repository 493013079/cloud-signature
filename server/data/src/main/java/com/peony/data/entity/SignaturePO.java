package com.peony.data.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @author 陈浩
 * @date 2020/9/8
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Builder
@Table(name = "signature")
public class SignaturePO extends BasePO<String> {

    public SignaturePO(String id) {
        this.setId(id);
    }

    /**
     * 主键，snowFlake算法生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.peony.common.util.CustomIDGenerator")
    private String id;

    /**
     * 笔画路径
     */
    @NotEmpty
    private String strokePath;

    /**
     * 签名图片文件存放路径
     */
    @NotEmpty
    private String value;

    /**
     * 票据 id
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private TicketsPO ticket;
}

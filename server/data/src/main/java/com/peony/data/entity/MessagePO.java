package com.peony.data.entity;

import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import lombok.*;

import javax.persistence.*;

/**
 * @author hk
 * @date 2019/10/26
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "message")
public class MessagePO extends BasePO<Integer> {

    public MessagePO(Integer id) {
        this.setId(id);
    }

    /**
     * 主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPO user;

    /**
     * 正文
     */
    private String content;

    /**
     * 消息类型
     */
    @Enumerated(EnumType.STRING)
    private MessageType type;

    /**
     * 阅读状态
     */
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

}

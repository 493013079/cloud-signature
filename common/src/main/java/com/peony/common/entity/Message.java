package com.peony.common.entity;

import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import lombok.*;

/**
 * 消息
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends AbstractAuditable implements Entity {

    /**
     * 消息ID
     */
    private Integer id;

    /**
     * 通知对象用户ID
     */
    private Integer userId;

    /**
     * 通知对象用户姓名
     */
    private String userName;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 阅读状态
     */
    private ReadStatus readStatus;

}

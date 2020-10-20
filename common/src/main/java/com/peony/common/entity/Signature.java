package com.peony.common.entity;

import lombok.*;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Signature extends AbstractAuditable implements Entity {

    /**
     * 签字ID
     */
    private String id;

    /**
     * 笔画路径
     */
    private String strokePath;

    /**
     * 签名图片存放路径
     */
    private String value;

    /**
     * 票据id
     */
    private String ticketId;

}

package com.peony.common.entity;

import lombok.*;

import java.util.List;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tickets extends AbstractAuditable implements Entity {

    /**
     * 签字票据ID
     */
    private String id;

    /**
     * 票据URL
     */
    private String targetURL;

    private List<Signature> signatures;

}

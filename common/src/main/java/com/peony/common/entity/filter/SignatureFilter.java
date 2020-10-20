package com.peony.common.entity.filter;

import com.peony.common.entity.Signature;
import lombok.Builder;
import lombok.Data;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
public class SignatureFilter implements EntityFilter<Signature> {

    private String strikePath;

    private String value;

    private String ticketId;

}

package com.peony.common.entity.filter;

import com.peony.common.entity.Tickets;
import lombok.Builder;
import lombok.Data;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
public class TicketsFilter implements EntityFilter<Tickets> {

    private String targetURL;

}

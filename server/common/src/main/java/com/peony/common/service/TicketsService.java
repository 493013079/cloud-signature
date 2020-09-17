package com.peony.common.service;

import com.peony.common.entity.Tickets;
import com.peony.common.entity.filter.TicketsFilter;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
public interface TicketsService extends CacheService<Tickets, String, TicketsFilter> {

}

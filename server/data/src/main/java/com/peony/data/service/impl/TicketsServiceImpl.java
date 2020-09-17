package com.peony.data.service.impl;

import com.peony.common.entity.Tickets;
import com.peony.common.entity.filter.TicketsFilter;
import com.peony.common.service.TicketsService;
import com.peony.data.converter.entity.TicketsConvert;
import com.peony.data.converter.predicate.TicketsFilterConverter;
import com.peony.data.entity.TicketsPO;
import com.peony.data.repository.TicketsRepository;
import org.springframework.stereotype.Service;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Service
public class TicketsServiceImpl extends CacheServiceImpl<Tickets, TicketsPO, String, TicketsFilter> implements TicketsService {

    public TicketsServiceImpl(TicketsRepository entityRepository,
                              TicketsConvert entityConverter,
                              TicketsFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}


package com.peony.signature.formatter;

import com.peony.common.entity.Tickets;
import com.peony.common.service.TicketsService;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

/**
 * @author hk
 */
@Component
public class TicketsFormatter implements Formatter<Tickets> {

    private final TicketsService ticketsService;

    public TicketsFormatter(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @Override
    @NonNull
    public Tickets parse(@NonNull String id, @NonNull Locale locale) {
        return Optional.of(ticketsService.findById(id))
                .orElseThrow(() -> new RestException(RestErrorCode.TICKETS_NOT_EXIST));
    }

    @Override
    @NonNull
    public String print(Tickets tickets, @NonNull Locale locale) {
        return String.valueOf(tickets.getId());
    }

}

package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.TicketsFilter;
import com.peony.data.entity.QTicketsPO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Component
public class TicketsFilterConverter implements EntityFilterToPredicateConverter<TicketsFilter> {

    @NonNull
    @Override
    public Predicate convert(TicketsFilter ticketsFilter) {
        Optional<String> optionalTargetURL = Optional.ofNullable(ticketsFilter.getTargetURL());

        QTicketsPO qTicketsPO = QTicketsPO.ticketsPO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        optionalTargetURL.map(qTicketsPO.targetURL::contains).ifPresent(booleanBuilder::or);
//        optionalSignatureId.map(qTicketsPO.signature.id::eq).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}

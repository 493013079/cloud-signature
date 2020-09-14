package com.peony.data.converter.predicate;

import com.peony.common.entity.filter.SignatureFilter;
import com.peony.data.entity.QSignaturePO;
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
public class SignatureFilterConvert implements EntityFilterToPredicateConverter<SignatureFilter> {

    @NonNull
    @Override
    public Predicate convert(SignatureFilter signatureFilter) {
        Optional<String> optionalStrikePath = Optional.ofNullable(signatureFilter.getStrikePath());
        Optional<String> optionalValue = Optional.ofNullable(signatureFilter.getValue());
        Optional<String> optionalTicketId = Optional.ofNullable(signatureFilter.getTicketId());

        QSignaturePO qSignaturePO = QSignaturePO.signaturePO;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        optionalStrikePath.map(qSignaturePO.strokePath::contains).ifPresent(booleanBuilder::or);
        optionalValue.map(qSignaturePO.value::contains).ifPresent(booleanBuilder::or);
        optionalValue.map(qSignaturePO.ticket.id::contains).ifPresent(booleanBuilder::and);
        optionalValue.map(qSignaturePO.ticket.targetURL::contains).ifPresent(booleanBuilder::and);
        optionalTicketId.map(qSignaturePO.ticket.id::endsWith).ifPresent(booleanBuilder::and);
        return booleanBuilder;
    }

}

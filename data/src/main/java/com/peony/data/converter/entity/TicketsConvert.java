package com.peony.data.converter.entity;

import com.peony.common.entity.Signature;
import com.peony.common.entity.Tickets;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.SignaturePO;
import com.peony.data.entity.TicketsPO;
import com.peony.data.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Component
public class TicketsConvert implements EntityConverter<TicketsPO, Tickets> {

    @Override
    public Tickets doForward(TicketsPO ticketsPO) {
        Tickets tickets = ConvertUtils.convert(ticketsPO, Tickets.class);
        Optional.ofNullable(ticketsPO.getSignatures()).ifPresent(signaturePOS -> {
            List<Signature> signatures = ConvertUtils.convert(signaturePOS, Signature.class);
            tickets.setSignatures(signatures);
        });
//        Optional.ofNullable(ticketsPO.getSignature())
//                .ifPresent(signaturePO -> {
//                    tickets.setSignatureId(signaturePO.getId());
//                    tickets.setSignatureStrokePath(signaturePO.getStrokePath());
//                    tickets.setSignatureValue(signaturePO.getValue());
//                });
        EntityUtils.copyAuditFields(ticketsPO, tickets);
        return tickets;
    }

    @Override
    public TicketsPO doBackward(Tickets tickets) {
        TicketsPO ticketsPO = ConvertUtils.convert(tickets, TicketsPO.class);
        Optional.ofNullable(tickets.getSignatures()).ifPresent(signatures -> {
            List<SignaturePO> signaturePOS = ConvertUtils.convert(signatures, SignaturePO.class);
            ticketsPO.setSignatures(signaturePOS);
        });
        return ticketsPO;
    }

}

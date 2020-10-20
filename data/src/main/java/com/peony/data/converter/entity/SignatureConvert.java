package com.peony.data.converter.entity;

import com.peony.common.entity.Signature;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.SignaturePO;
import com.peony.data.entity.TicketsPO;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Component
public class SignatureConvert implements EntityConverter<SignaturePO, Signature> {

    @Override
    public Signature doForward(SignaturePO signaturePO) {
        Signature signature = ConvertUtils.convert(signaturePO, Signature.class);
        Optional.ofNullable(signaturePO.getTicket())
                .ifPresent(ticketsPO -> {
                    signature.setTicketId(ticketsPO.getId());
                });
        return signature;
    }

    @Override
    public SignaturePO doBackward(Signature signature) {
        SignaturePO signaturePO = ConvertUtils.convert(signature, SignaturePO.class);
        Optional.ofNullable(signature.getTicketId())
                .map(TicketsPO::new)
                .ifPresent(signaturePO::setTicket);
        return signaturePO;
    }

}

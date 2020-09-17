package com.peony.signature.formatter;

import com.peony.common.entity.Signature;
import com.peony.common.service.SignatureService;
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
public class SignatureFormatter implements Formatter<Signature> {

    private final SignatureService signatureService;

    public SignatureFormatter(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @Override
    @NonNull
    public Signature parse(@NonNull String id, @NonNull Locale locale) {
        return Optional.of(signatureService.findById(id))
                .orElseThrow(() -> new RestException(RestErrorCode.SIGNATURE_NOT_EXIST));
    }

    @Override
    @NonNull
    public String print(Signature signature, @NonNull Locale locale) {
        return String.valueOf(signature.getId());
    }

}

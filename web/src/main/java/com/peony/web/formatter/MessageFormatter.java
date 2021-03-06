package com.peony.web.formatter;

import com.peony.common.entity.Message;
import com.peony.common.service.MessageService;
import com.peony.common.web.RestErrorCode;
import com.peony.common.web.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

/**
 * @author hk
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageFormatter implements Formatter<Message> {

    private final MessageService MessageService;

    @Override
    @NonNull
    public Message parse(String id, @NonNull Locale locale) {
        if (id.isEmpty()) {
            throw new RestException(RestErrorCode.WRONG_PARAM);
        }
        return Optional.of(MessageService.findById(Integer.valueOf(id))).orElseThrow(() -> {
            log.error("消息记录不存在, id:{}", id);
            return new RestException(RestErrorCode.MESSAGE_NOT_EXIST);
        });
    }

    @Override
    @NonNull
    public String print(Message message, @NonNull Locale locale) {
        return message.getId().toString();
    }

}

package com.peony.data.converter.entity;

import com.peony.common.entity.Message;
import com.peony.common.util.ConvertUtils;
import com.peony.data.entity.MessagePO;
import com.peony.data.entity.UserPO;
import com.peony.data.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@Component
public class MessageConverter implements EntityConverter<MessagePO, Message> {

    @Override
    public Message doForward(MessagePO messagePO) {
        Message message = ConvertUtils.convert(messagePO, Message.class);
        EntityUtils.copyAuditFields(messagePO, message);
        Optional.ofNullable(messagePO.getUser())
                .ifPresent(userPO -> {
                    message.setUserId(userPO.getId());
                    message.setUserName(userPO.getName());
                });
        return message;
    }

    @Override
    public MessagePO doBackward(Message message) {
        MessagePO messagePO = ConvertUtils.convert(message, MessagePO.class);
        Optional.ofNullable(message.getUserId())
                .map(UserPO::new)
                .ifPresent(messagePO::setUser);
        return messagePO;
    }

}

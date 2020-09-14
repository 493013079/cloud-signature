package com.peony.data.service.impl;

import com.peony.common.entity.Message;
import com.peony.common.entity.filter.MessageFilter;
import com.peony.common.service.MessageService;
import com.peony.data.converter.entity.MessageConverter;
import com.peony.data.converter.predicate.MessageFilterConverter;
import com.peony.data.entity.MessagePO;
import com.peony.data.repository.MessageRepository;
import org.springframework.stereotype.Service;

/**
 * @author hk
 * @date 2019/10/24
 */
@Service
public class MessageServiceImpl extends AbstractEntityServiceImpl<Message, MessagePO, Integer, MessageFilter> implements MessageService {

    public MessageServiceImpl(MessageRepository entityRepository,
                              MessageConverter entityConverter,
                              MessageFilterConverter entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

}

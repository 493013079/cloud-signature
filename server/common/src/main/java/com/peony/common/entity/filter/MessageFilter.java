package com.peony.common.entity.filter;

import com.peony.common.entity.Message;
import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author hk
 * @date 2019/10/24
 */
@Data
@Builder
public class MessageFilter implements EntityFilter<Message> {

    private String keyword;

    private Integer userId;

    private String organizationPath;

    private MessageType type;

    private ReadStatus readStatus;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

}

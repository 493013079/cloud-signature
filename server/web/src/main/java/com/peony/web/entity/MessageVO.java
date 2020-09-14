package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.peony.common.entity.field.MessageType;
import com.peony.common.entity.field.ReadStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author hk
 * @date 2019/10/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVO extends AbstractAuditableVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private MessageType type;

    @ApiModelProperty(value = "阅读状态")
    private ReadStatus readStatus;

}

package com.peony.web.entity.form.message;

import com.peony.common.entity.field.MessageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hk
 * @date 2019/10/26
 */
@ApiModel(value = "消息对象", description = "消息对象VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAddFormVO {

    @NotEmpty
    @ApiModelProperty(value = "用户ID", example = "1000001", required = true)
    private List<Integer> userIds;

    @NotBlank
    @ApiModelProperty(value = "消息内容", example = "测试消息内容", required = true)
    private String content;

    @NotNull
    @ApiModelProperty(value = "消息类型, 字典MessageType获取", required = true)
    private MessageType type;

}

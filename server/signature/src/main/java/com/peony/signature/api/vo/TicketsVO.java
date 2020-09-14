package com.peony.signature.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
public class TicketsVO {

    @ApiModelProperty(value = "票据URL")
    @NotEmpty
    private String targetURL;

    /**
     * 签字图片存放路径
     */
    @ApiModelProperty(value = "签字数据")
    private List<String> signatureValues;

}

package com.peony.signature.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Data
@Builder
public class SignatureVO {

    /**
     * 笔画路径
     */
    @ApiModelProperty(value = "笔画路径")
    private String strokePath;

    /**
     * 签字图片存放路径
     */
    @ApiModelProperty(value = "签字数据存放路径")
    private String value;

}

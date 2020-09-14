package com.peony.web.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 杜云山
 * @date 19/10/26
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "字典key")
    private String key;

    @ApiModelProperty(value = "字典值")
    private String value;

}

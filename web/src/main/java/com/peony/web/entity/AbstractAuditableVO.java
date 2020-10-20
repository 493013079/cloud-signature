package com.peony.web.entity;

import com.peony.common.entity.Auditable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author 辛毅
 * @date 2019/11/27
 */
@Data
public abstract class AbstractAuditableVO implements Auditable {

    @ApiModelProperty(value = "创建人ID")
    private Integer createdById;

    @ApiModelProperty(value = "创建人姓名")
    private String createdByName;

    @ApiModelProperty(value = "创建时间")
    private ZonedDateTime createdDate;

    @ApiModelProperty(value = "最后修改人ID")
    private Integer lastModifiedById;

    @ApiModelProperty(value = "最后修改人姓名")
    private String lastModifiedByName;

    @ApiModelProperty(value = "最后修改时间")
    private ZonedDateTime lastModifiedDate;

}

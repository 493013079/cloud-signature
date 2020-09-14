package com.peony.web.entity;

import com.peony.common.entity.field.AuditLogType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author 辛毅
 * @date 2019/12/3
 */
@Data
public class AuditLogVO {

    @ApiModelProperty(value = "审计日志ID")
    private Integer id;

    @ApiModelProperty(value = "操作类型")
    private AuditLogType type;

    @ApiModelProperty(value = "对象类型")
    private String objectType;

    @ApiModelProperty(value = "操作对象ID")
    private String objectId;

    @ApiModelProperty(value = "操作用户ID")
    private Integer userId;

    @ApiModelProperty(value = "操作用户姓名")
    private String userName;

    @ApiModelProperty(value = "创建时间")
    private ZonedDateTime createdDate;

}

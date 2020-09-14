package com.peony.common.entity;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author 辛毅
 * @date 2019/11/27
 */
@Data
public abstract class AbstractAuditable implements Auditable {

    /**
     * 创建人ID
     */
    private Integer createdById;

    /**
     * 创建人姓名
     */
    private String createdByName;

    /**
     * 创建时间
     */
    private ZonedDateTime createdDate;

    /**
     * 最后修改人ID
     */
    private Integer lastModifiedById;

    /**
     * 最后修改人姓名
     */
    private String lastModifiedByName;

    /**
     * 最后修改时间
     */
    private ZonedDateTime lastModifiedDate;

}

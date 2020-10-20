package com.peony.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 实体抽象接口
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Entity {

    /**
     * 获取实体ID
     *
     * @return id
     */
    Object getId();

}

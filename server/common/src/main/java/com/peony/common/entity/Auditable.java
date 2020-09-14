package com.peony.common.entity;

import java.time.ZonedDateTime;

/**
 * 审计实体接口
 *
 * @author 辛毅
 * @date 2019/11/27
 */
public interface Auditable {

    /**
     * 获取创建人ID
     *
     * @return 创建人ID
     */
    Integer getCreatedById();

    /**
     * 设置创建人ID
     *
     * @param id 创建人ID
     */
    void setCreatedById(Integer id);

    /**
     * 获取创建人姓名
     *
     * @return 创建人姓名
     */
    String getCreatedByName();

    /**
     * 设置创建人姓名
     *
     * @param name 创建人姓名
     */
    void setCreatedByName(String name);

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    ZonedDateTime getCreatedDate();

    /**
     * 设置创建时间
     *
     * @param createdDate 创建时间
     */
    void setCreatedDate(ZonedDateTime createdDate);

    /**
     * 获取最后修改人ID
     *
     * @return 最后修改人ID
     */
    Integer getLastModifiedById();

    /**
     * 设置最后修改人ID
     *
     * @param id 最后修改人ID
     */
    void setLastModifiedById(Integer id);

    /**
     * 获取最后修改人姓名
     *
     * @return 最后修改人姓名
     */
    String getLastModifiedByName();

    /**
     * 设置最后修改人姓名
     *
     * @param name 最后修改人姓名
     */
    void setLastModifiedByName(String name);

    /**
     * 获取最后修改时间
     *
     * @return 最后修改时间
     */
    ZonedDateTime getLastModifiedDate();

    /**
     * 设置最后修改时间
     *
     * @param lastModifiedDate 最后修改时间
     */
    void setLastModifiedDate(ZonedDateTime lastModifiedDate);

}

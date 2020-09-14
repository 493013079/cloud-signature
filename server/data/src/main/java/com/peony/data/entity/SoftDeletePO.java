package com.peony.data.entity;

/**
 * @author 辛毅
 * @date 2019/11/25
 */
public interface SoftDeletePO {

    /**
     * 是否生效
     *
     * @return active
     */
    Boolean getActive();

    /**
     * 设置生效状态
     *
     * @param active active
     */
    void setActive(Boolean active);

}

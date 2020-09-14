package com.peony.common.entity;

import lombok.Data;

/**
 * 字典
 *
 * @author 辛毅
 * @date 2019/11/22
 */
@Data
public class Dict implements Entity {

    /**
     * 字典ID
     */
    private Integer id;

    /**
     * 字典分组
     */
    private String type;

    /**
     * 字典KEY
     */
    private String key;

    /**
     * 字典值
     */
    private String value;

}

package com.peony.data.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hk
 * @date 2019/10/24
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "dict")
public class DictPO extends BasePO<Integer> {

    /**
     * 主键
     */
    @Id
    private Integer id;

    private String type;

    private String key;

    private String value;

}

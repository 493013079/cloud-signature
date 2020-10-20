package com.peony.data.entity;

import com.peony.data.config.AuditLogListener;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author hk
 */
@MappedSuperclass
@Data
@EntityListeners({
        AuditingEntityListener.class,
        AuditLogListener.class
})
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
public abstract class BasePO<ID extends Serializable> implements AuditablePO<ID>, Serializable {

    @CreatedBy
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private UserPO createdBy;

    @CreatedDate
    @Column(updatable = false)
    private ZonedDateTime createdDate;

    @LastModifiedBy
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPO lastModifiedBy;

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate;

    private Boolean active = true;

}
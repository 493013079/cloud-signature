package com.peony.web.aop;

import com.peony.common.entity.field.RoleType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hk
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {

    @AliasFor("roles")
    RoleType[] value() default {};

    @AliasFor("value")
    RoleType[] roles() default {};

}

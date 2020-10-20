package com.peony.web.aop;

import com.peony.common.entity.field.PermissionKey;
import com.peony.common.web.Logical;
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
public @interface RequirePermissions {

    @AliasFor("permissions")
    PermissionKey[] value() default {};

    @AliasFor("value")
    PermissionKey[] permissions() default {};

    Logical logic() default Logical.AND;

}

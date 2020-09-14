package com.peony.data.annotation;

import com.peony.common.web.RestErrorCode;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author hk
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = FieldRepeatValidator.class)
public @interface FieldRepeatValidate {

    /**
     * 注解属性 - 对应校验字段
     */
    String field();

    /**
     * 默认错误提示信息
     */
    RestErrorCode code();

//    Class<?>[] groups() default {};
//    Class<? extends Payload>[]  payload() default {};

}

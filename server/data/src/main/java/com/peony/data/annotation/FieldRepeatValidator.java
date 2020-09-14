package com.peony.data.annotation;

import com.peony.common.web.RestErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * initialize 方法主要来进行初始化，通常用来获取自定义注解的属性值；
 * isValid 方法主要进行校验逻辑，返回true表示校验通过，返回false表示校验失败，通常根据注解属性值和实体类属性值进行校验判断
 * [Object:校验字段的属性值]
 *
 * @author hk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FieldRepeatValidator implements ConstraintValidator<FieldRepeatValidate, Object> {

    private final EntityManager entityManager;

    private final String ID = "id";

    /**
     * 表单中id值
     */
    private Integer idValue;

    /**
     * 校验字段
     */
    private String field;

    /**
     * 表单值
     */
    private Object fieldValue;

    /**
     * 校验字段 - 对应数据库字段
     */
    private String db_field;

    private RestErrorCode code;

    @Override
    public void initialize(FieldRepeatValidate fieldRepeatValidator) {
        this.field = fieldRepeatValidator.field();
        this.code = fieldRepeatValidator.code();
    }

    @Override
    public boolean isValid(Object formVO, ConstraintValidatorContext constraintValidatorContext) {
        initFieldValue(formVO);
        return fieldRepeat(field, formVO, code);
    }

    /**
     * 校验数据
     *
     * @param field：校验字段
     * @param formVO：表单对象
     * @param code：错误提示码
     */
    private boolean fieldRepeat(String field, Object formVO, RestErrorCode code) {
//        Map<String, Object> map = new HashMap<>(2);
//
//        map.put(field, fieldValue);
//        entityManager.findById()
//        //todo 判断重复逻辑
//        if(StringUtils.isNotEmpty(idValue)){
//
//        }
        return false;
    }

    /**
     * 获取表单中的id、校验字段值
     */
    private void initFieldValue(Object formVO) {
        //获取对象所有的字段
        Field[] fields = formVO.getClass().getDeclaredFields();
        for (Field formfield : fields) {
            //设置对象中成员 属性private为可读
            formfield.setAccessible(true);
            if (formfield.isAnnotationPresent(FieldRepeatValidate.class)) {
                if (formfield.getName().equals(this.field)) {
                    try {
                        fieldValue = formfield.get(formVO);
                        Column annotation = formfield.getAnnotation(Column.class);
                        db_field = annotation.name();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (ID.equals(formfield.getName())) {
                    try {
                        idValue = (Integer) formfield.get(formVO);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

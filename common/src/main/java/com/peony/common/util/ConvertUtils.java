package com.peony.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 辛毅
 * @date 2019/11/22
 */
@UtilityClass
public class ConvertUtils {

    /**
     * 类型转换，拷贝属性
     *
     * @param source      源对象
     * @param targetClass 目标类型类
     * @param <T>         目标类型
     * @return 目标对象
     */
    public <T> T convert(@NonNull Object source, @NonNull Class<T> targetClass) {
        try {
            Constructor<T> constructor = targetClass.getConstructor();
            T t = constructor.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 类型转换，拷贝属性
     *
     * @param sources     当前List对象
     * @param targetClass 目标类型类
     * @param <T>         目标类型
     * @return 目标List对象
     */
    public <T> List<T> convert(@NonNull List<?> sources, @NonNull Class<T> targetClass) {
        return sources.stream()
                .map(source -> convert(source, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * 类型转换，拷贝属性
     *
     * @param sources     当前Page对象
     * @param targetClass 目标类型类
     * @param <T>         目标类型
     * @return 目标Page对象
     */
    public <T> Page<T> convert(@NonNull Page<?> sources, @NonNull Class<T> targetClass) {
        return sources.map(source -> convert(source, targetClass));
    }

}

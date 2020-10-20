package com.peony.data.converter.entity;

/**
 * 实体转换
 *
 * @author 辛毅
 * @date 2019/11/22
 */
public interface EntityConverter<S, T> {

    /**
     * 将S类型对象转成T类型对象
     *
     * @param s S类型对象
     * @return T类型对象
     */
    T doForward(S s);

    /**
     * 将T类型对象转成S类型对象
     *
     * @param t T类型对象
     * @return S类型对象
     */
    S doBackward(T t);

}

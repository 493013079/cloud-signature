package com.peony.common.service;

import com.peony.common.entity.Entity;
import com.peony.common.entity.filter.EntityFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author hk
 */
public interface EntityService<ENTITY extends Entity, ID, FILTER extends EntityFilter<ENTITY>> {

    /**
     * 保存或更新实体
     *
     * @param entity 实体
     * @return 实体
     */
    @NonNull
    ENTITY save(@NonNull ENTITY entity);

    /**
     * 删除实体
     *
     * @param id 实体ID
     */
    void deleteById(@NonNull ID id);

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    void delete(@NonNull ENTITY entity);

    /**
     * 删除所有实体
     *
     * @param filter 实体过滤条件
     */
    void deleteAll(@NonNull FILTER filter);

    /**
     * 查询某一实体
     *
     * @param id 实体ID
     * @return 实体
     */
    @NonNull
    Optional<ENTITY> findById(@NonNull ID id);

    /**
     * 分页查询实体
     *
     * @param pageable 分页条件
     * @return 实体分页结果
     */
    @NonNull
    Page<ENTITY> findAll(@NonNull Pageable pageable);

    /**
     * 条件查询实体
     *
     * @param entityFilter 实体过滤条件
     * @param pageable     分页条件
     * @return 实体过滤分页结果
     */
    @NonNull
    Page<ENTITY> findAll(@NonNull FILTER entityFilter, @NonNull Pageable pageable);

    /**
     * 查询所有实体
     *
     * @return 实体列表
     */
    @NonNull
    List<ENTITY> findAll();

    /**
     * 查询所有实体
     *
     * @param entityFilter 实体过滤条件
     * @return 实体列表
     */
    @NonNull
    List<ENTITY> findAll(@NonNull FILTER entityFilter);

    /**
     * 查询某一实体
     *
     * @param entityFilter 实体过滤条件
     * @return 实体
     */
    @NonNull
    Optional<ENTITY> findOne(@NonNull FILTER entityFilter);

    /**
     * 查询数量
     *
     * @param entityFilter 实体过滤条件
     * @return 数量
     */
    long count(@NonNull FILTER entityFilter);

}
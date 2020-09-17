package com.peony.data.service.impl;

import com.google.common.collect.Lists;
import com.peony.common.entity.Entity;
import com.peony.common.entity.filter.EntityFilter;
import com.peony.common.service.CacheService;
import com.peony.data.config.CacheConfig;
import com.peony.data.converter.entity.EntityConverter;
import com.peony.data.converter.predicate.EntityFilterToPredicateConverter;
import com.peony.data.entity.SoftDeletePO;
import com.peony.data.repository.EntityRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @Description: 加入缓存的公用service实现类
 * @Author: li.tiancheng
 * @Date: 2020/9/15 15:27
 *
 **/
public class CacheServiceImpl<ENTITY extends Entity, PO extends SoftDeletePO, ID, FILTER extends EntityFilter<ENTITY>> extends AbstractEntityServiceImpl<ENTITY, PO, ID, FILTER> implements CacheService<ENTITY,ID,FILTER> {

    public CacheServiceImpl(EntityRepository<PO, ID> entityRepository, EntityConverter<PO, ENTITY> entityConverter, EntityFilterToPredicateConverter<FILTER> entityFilterConverter) {
        super(entityRepository, entityConverter, entityFilterConverter);
    }

    /**
     * 保存或更新实体
     *
     * @param entity 实体
     * @return 实体
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = true, useMD5 = false)
    public ENTITY save(@NonNull ENTITY entity) {
        return Optional.of(entity)
                .map(entityConverter::doBackward)
                .map(entityRepository::save)
                .map(entityConverter::doForward)
                .get();
    }

    /**
     * 软删除实体
     *
     * @param id 实体ID
     */
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = true, useMD5 = false)
    public void deleteById(@NonNull ID id) {
        entityRepository.findById(id)
                .filter(SoftDeletePO::getActive)
                .ifPresent(po -> {
                    po.setActive(false);
                    entityRepository.save(po);
                });
    }

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = true, useMD5 = false)
    public void delete(@NonNull ENTITY entity) {
        PO po = entityConverter.doBackward(entity);
        po.setActive(false);
        entityRepository.save(po);
    }

    /**
     * 软删除所有实体
     *
     * @param entityFilter 实体过滤条件
     */
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = true, useMD5 = false)
    public void deleteAll(@NonNull FILTER entityFilter) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        Iterable<PO> entities = entityRepository.findAll(predicate);
        entities.forEach(entry -> entry.setActive(false));

        entityRepository.saveAll(entities);
    }

    /**
     * 查询某一实体
     *
     * @param id 实体ID
     * @return 实体
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public ENTITY findById(@NonNull ID id) {
        return entityRepository.findById(id)
                .filter(SoftDeletePO::getActive)
                .map(entityConverter::doForward).get();
    }

    /**
     * 分页查询实体
     *
     * @param pageable 分页条件
     * @return 实体分页结果
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public Page<ENTITY> findAll(@NonNull Pageable pageable) {
        return entityRepository.findAll(whereActive(), pageable)
                .map(entityConverter::doForward);
    }

    /**
     * 条件查询实体
     *
     * @param entityFilter 实体过滤条件
     * @param pageable     分页条件
     * @return 实体过滤分页结果
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public Page<ENTITY> findAll(@NonNull FILTER entityFilter, @NonNull Pageable pageable) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        return entityRepository.findAll(predicate, pageable).map(entityConverter::doForward);
    }

    /**
     * 查询所有实体
     *
     * @return 实体列表
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public List<ENTITY> findAll() {
        return StreamSupport.stream(entityRepository.findAll(whereActive()).spliterator(), false)
                .map(entityConverter::doForward)
                .collect(Collectors.toList());
    }

    /**
     * 查询所有实体
     *
     * @param entityFilter 实体过滤条件
     * @return 实体列表
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public List<ENTITY> findAll(@NonNull FILTER entityFilter) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        return Lists.newArrayList(entityRepository.findAll(predicate)).stream()
                .map(entityConverter::doForward)
                .collect(Collectors.toList());
    }

    /**
     * 查询某一实体
     *
     * @param entityFilter 实体过滤条件
     * @return 实体列表
     */
    @NonNull
    @Override
    @CacheConfig(namespace = "", key = "", time = CacheConfig.TIME_HOUR, deleteNamespace = false, useMD5 = false)
    public ENTITY findOne(@NonNull FILTER entityFilter) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        return entityRepository.findOne(predicate).map(entityConverter::doForward).get();
    }

    /**
     * 查询数量
     *
     * @param entityFilter 实体过滤条件
     * @return 数量
     */
    @Override
    public long count(@NonNull FILTER entityFilter) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        return entityRepository.count(predicate);
    }

    /**
     * 未软删除条件
     *
     * @param predicate 查询条件
     * @return 查询条件 and active=true
     */
    private Predicate whereActive(Predicate predicate) {
        return new BooleanBuilder(whereActive()).and(predicate);
    }

    /**
     * 未软删除条件
     *
     * @return where active=true
     */
    private Predicate whereActive() {
        return Expressions.booleanPath("active").eq(true);
    }
}

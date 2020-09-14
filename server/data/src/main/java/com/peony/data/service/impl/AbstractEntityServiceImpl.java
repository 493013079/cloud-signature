package com.peony.data.service.impl;

import com.google.common.collect.Lists;
import com.peony.common.entity.Entity;
import com.peony.common.entity.filter.EntityFilter;
import com.peony.common.service.EntityService;
import com.peony.data.converter.entity.EntityConverter;
import com.peony.data.converter.predicate.EntityFilterToPredicateConverter;
import com.peony.data.entity.SoftDeletePO;
import com.peony.data.repository.EntityRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 抽象实体服务实现
 *
 * @author 辛毅
 * @date 2019/4/19
 */
@AllArgsConstructor
public abstract class AbstractEntityServiceImpl<ENTITY extends Entity, PO extends SoftDeletePO, ID, FILTER extends EntityFilter<ENTITY>> implements EntityService<ENTITY, ID, FILTER> {

    /**
     * 实体仓库
     */
    protected final EntityRepository<PO, ID> entityRepository;

    /**
     * 实体类型转换工具
     */
    protected final EntityConverter<PO, ENTITY> entityConverter;

    /**
     * 实体过滤条件转换工具
     */
    protected final EntityFilterToPredicateConverter<FILTER> entityFilterConverter;

    /**
     * 保存或更新实体
     *
     * @param entity 实体
     * @return 实体
     */
    @NonNull
    @Override
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
    public Optional<ENTITY> findById(@NonNull ID id) {
        return entityRepository.findById(id)
                .filter(SoftDeletePO::getActive)
                .map(entityConverter::doForward);
    }

    /**
     * 分页查询实体
     *
     * @param pageable 分页条件
     * @return 实体分页结果
     */
    @NonNull
    @Override
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
    public Optional<ENTITY> findOne(@NonNull FILTER entityFilter) {
        Predicate predicate = entityFilterConverter.convert(entityFilter);
        predicate = whereActive(predicate);
        return entityRepository.findOne(predicate).map(entityConverter::doForward);
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

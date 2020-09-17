package com.peony.common.service;

import com.peony.common.entity.Entity;
import com.peony.common.entity.filter.EntityFilter;

/**
 * @Description: 缓存公用接口
 * @Author: li.tiancheng
 * @Date: 2020/9/15 15:11
 *
 **/
public interface CacheService<ENTITY extends Entity, ID, FILTER extends EntityFilter<ENTITY>> extends EntityService<ENTITY,ID,FILTER> {

}

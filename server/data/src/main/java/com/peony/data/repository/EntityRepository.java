package com.peony.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author hk
 * @date 2019/10/24
 */
@NoRepositoryBean
public interface EntityRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

}

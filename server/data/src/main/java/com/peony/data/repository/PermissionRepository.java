package com.peony.data.repository;

import com.peony.data.entity.PermissionPO;
import org.springframework.stereotype.Repository;

/**
 * @author hk
 * @date 2019/10/24
 */
@Repository
public interface PermissionRepository extends EntityRepository<PermissionPO, Integer> {

}

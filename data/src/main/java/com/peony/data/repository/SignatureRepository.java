package com.peony.data.repository;

import com.peony.data.entity.SignaturePO;
import org.springframework.stereotype.Repository;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Repository
public interface SignatureRepository extends EntityRepository<SignaturePO, String> {

}

package com.peony.data.repository;

import com.peony.data.entity.MessagePO;
import org.springframework.stereotype.Repository;

/**
 * @author hk
 * @date 2019/10/24
 */
@Repository
public interface MessageRepository extends EntityRepository<MessagePO, Integer> {

}

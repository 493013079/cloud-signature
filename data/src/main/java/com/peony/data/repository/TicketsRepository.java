package com.peony.data.repository;

import com.peony.data.entity.TicketsPO;
import org.springframework.stereotype.Repository;

/**
 * @author 陈浩
 * @date 2020/9/8
 */
@Repository
public interface TicketsRepository extends EntityRepository<TicketsPO, String> {

}

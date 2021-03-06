package org.bg181.sbd.infra.repository;

import org.bg181.sbd.infra.repository.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
public interface OrderEntityRepository extends PagingAndSortingRepository<OrderEntity, String> {
}

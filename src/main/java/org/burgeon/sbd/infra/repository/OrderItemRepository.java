package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.infra.repository.entity.OrderItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItemEntity, String> {
}

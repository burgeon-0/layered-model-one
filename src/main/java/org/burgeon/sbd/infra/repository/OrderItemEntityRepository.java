package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.infra.repository.entity.OrderItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
public interface OrderItemEntityRepository extends PagingAndSortingRepository<OrderItemEntity, String> {

    /**
     * 批量查询订单项
     *
     * @param orderNos
     * @return
     */
    List<OrderItemEntity> findAllByOrderNoIn(List<String> orderNos);

    /**
     * 批量查询订单项
     *
     * @param orderNo
     * @return
     */
    List<OrderItemEntity> findAllByOrderNo(String orderNo);

}

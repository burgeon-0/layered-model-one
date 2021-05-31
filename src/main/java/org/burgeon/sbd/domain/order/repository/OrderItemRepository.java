package org.burgeon.sbd.domain.order.repository;

import org.burgeon.sbd.domain.order.OrderItem;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public interface OrderItemRepository {

    /**
     * 保存订单子项
     *
     * @param orderItems
     */
    void save(List<OrderItem> orderItems);

}

package org.burgeon.sbd.domain.order.repository;

import org.burgeon.sbd.domain.order.OrderAggregate;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public interface OrderRepository {

    /**
     * 加载订单
     *
     * @param orderNo
     * @return
     */
    OrderAggregate load(String orderNo);

    /**
     * 保存订单
     *
     * @param order
     */
    void save(OrderAggregate order);

}

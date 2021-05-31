package org.burgeon.sbd.domain.order.factory;

import org.burgeon.sbd.domain.ApplicationContextHolder;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.repository.OrderRepository;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class OrderFactory {

    public static final OrderFactory INSTANCE = new OrderFactory();

    public static OrderFactory getInstance() {
        return INSTANCE;
    }

    private OrderRepository orderRepository;

    public OrderFactory() {
        orderRepository = ApplicationContextHolder.getBean(OrderRepository.class);
    }

    public OrderAggregate load(String orderNo) {
        return orderRepository.load(orderNo);
    }

}

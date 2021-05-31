package org.burgeon.sbd.domain.order.factory;

import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderFactory {

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    public OrderFactory getInstance() {
        return new OrderFactory();
    }

    public OrderAggregate load(String orderNo) {
        return orderRepository.load(orderNo);
    }

}

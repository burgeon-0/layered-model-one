package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public OrderAggregate load(String orderNo) {
        return null;
    }

    @Override
    public void save(OrderAggregate order) {

    }

}

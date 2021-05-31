package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.DomainRepository;
import org.burgeon.sbd.domain.order.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderItemRepositoryImpl implements DomainRepository<OrderItem, String> {

    @Override
    public OrderItem load(String s) {
        return null;
    }

    @Override
    public void save(OrderItem orderItem) {

    }

    @Override
    public void save(List<OrderItem> orderItems) {

    }

}

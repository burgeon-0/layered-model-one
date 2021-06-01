package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public abstract class AbstractOrderRepositoryImpl implements DomainRepository<OrderAggregate, String> {

    private Map<String, OrderAggregate> orderMap = new HashMap<>();

    @Override
    public OrderAggregate load(String orderNo) {
        return orderMap.get(orderNo);
    }

    @Override
    public void save(OrderAggregate order) {
        orderMap.put(order.getOrderNo(), order);
    }

}

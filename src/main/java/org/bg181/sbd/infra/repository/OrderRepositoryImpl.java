package org.bg181.sbd.infra.repository;

import org.bg181.sbd.core.AbstractDomainRepository;
import org.bg181.sbd.domain.order.OrderAggregate;
import org.bg181.sbd.infra.repository.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderRepositoryImpl extends AbstractDomainRepository<OrderAggregate, String> {

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Override
    public OrderAggregate load(String orderNo) {
        Optional<OrderEntity> optional = orderEntityRepository.findById(orderNo);
        if (optional.isPresent()) {
            OrderEntity orderEntity = optional.get();
            OrderAggregate orderAggregate = orderEntity.to(OrderAggregate.class);
            return orderAggregate;
        }
        return null;
    }

    @Override
    public void save(OrderAggregate order) {
        OrderEntity orderEntity = order.to(OrderEntity.class);
        orderEntityRepository.save(orderEntity);
    }

}

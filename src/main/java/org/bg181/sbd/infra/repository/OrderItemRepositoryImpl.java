package org.bg181.sbd.infra.repository;

import org.bg181.sbd.core.AbstractDomainRepository;
import org.bg181.sbd.core.base.OrderItem;
import org.bg181.sbd.infra.repository.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderItemRepositoryImpl extends AbstractDomainRepository<OrderItem, String> {

    @Autowired
    private OrderItemEntityRepository orderItemEntityRepository;

    @Override
    public void save(List<OrderItem> orderItems) {
        List<OrderItemEntity> orderItemEntities = orderItems.stream().map(
                orderItem -> orderItem.to(OrderItemEntity.class)).collect(Collectors.toList());
        orderItemEntityRepository.saveAll(orderItemEntities);
    }

}

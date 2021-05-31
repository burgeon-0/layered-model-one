package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.order.OrderItem;
import org.burgeon.sbd.domain.order.repository.OrderItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderItemRepositoryImpl implements OrderItemRepository {

    @Override
    public void save(List<OrderItem> orderItems) {

    }

}

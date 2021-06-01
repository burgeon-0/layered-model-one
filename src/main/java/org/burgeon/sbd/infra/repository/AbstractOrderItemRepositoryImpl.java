package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.core.base.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public abstract class AbstractOrderItemRepositoryImpl implements DomainRepository<OrderItem, String> {

    @Override
    public void save(List<OrderItem> orderItems) {

    }

}

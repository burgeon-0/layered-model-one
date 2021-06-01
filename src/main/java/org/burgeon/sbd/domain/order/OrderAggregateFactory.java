package org.burgeon.sbd.domain.order;

import org.burgeon.sbd.core.DomainFactory;
import org.burgeon.sbd.core.SpringBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class OrderAggregateFactory extends DomainFactory<OrderAggregate, String> {

    @Override
    public OrderAggregate load(String orderNo) {
        domainRepository = SpringBeanFactory.getDomainRepository(OrderAggregate.class, String.class);
        return super.load(orderNo);
    }

}

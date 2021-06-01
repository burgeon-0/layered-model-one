package org.burgeon.sbd.domain.order.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.DomainEvent;
import org.burgeon.sbd.core.base.OrderItem;

import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlaceOrderEvent implements DomainEvent {

    private String orderNo;
    private List<OrderItem> items;
    private int totalPrice;
    private Date placeTime;

}

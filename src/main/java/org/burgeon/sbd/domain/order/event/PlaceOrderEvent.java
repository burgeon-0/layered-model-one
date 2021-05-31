package org.burgeon.sbd.domain.order.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.DomainEvent;

import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlaceOrderEvent extends DomainEvent {

    private String orderNo;
    private List<Item> items;
    private Date placeTime;

    @Data
    public static class Item {

        private String productNo;
        private String productName;
        private Integer totalCount;
        private Integer totalPrice;

    }

}

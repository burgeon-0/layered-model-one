package org.burgeon.sbd.domain.order.event;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class PlaceOrderEvent {

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

package org.burgeon.sbd.domain.order;

import lombok.Getter;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class OrderAggregate {

    @Getter
    private String orderNo;
    private String productNo;
    private String productName;
    private int totalCount;
    private int totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private int status;

    public OrderAggregate(PlaceOrderCommand placeOrderCommand) {
        this.productNo = placeOrderCommand.getProductNo();
        this.productName = placeOrderCommand.getProductName();
        this.totalCount = placeOrderCommand.getTotalCount();
        this.totalPrice = placeOrderCommand.getTotalPrice();
        this.placeTime = placeOrderCommand.getPlaceTime();
        // todo store order

        PlaceOrderEvent placeOrderEvent = placeOrderCommand.to(PlaceOrderEvent.class);
        // todo publish event
    }

}

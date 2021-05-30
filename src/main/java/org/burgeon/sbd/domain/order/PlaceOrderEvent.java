package org.burgeon.sbd.domain.order;

import lombok.Data;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class PlaceOrderEvent {

    private String productNo;
    private String productName;
    private int totalCount;
    private int totalPrice;
    private Date placeTime;

}

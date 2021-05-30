package org.burgeon.sbd.domain.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.BaseObject;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlaceOrderCommand extends BaseObject {

    private String productNo;
    private String productName;
    private int totalCount;
    private int totalPrice;
    private Date placeTime;

}

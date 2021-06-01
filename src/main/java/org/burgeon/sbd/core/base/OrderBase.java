package org.burgeon.sbd.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderBase extends Base {

    private List<OrderItem> items;
    private Integer totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private Integer status;

}

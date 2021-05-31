package org.burgeon.sbd.domain.order.event;

import lombok.Data;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class PayOrderEvent {

    private String orderNo;
    private Date payTime;

}
